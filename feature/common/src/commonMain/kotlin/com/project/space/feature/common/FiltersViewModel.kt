package com.project.space.feature.common

import com.libraries.utils.PlatformScopeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class FiltersViewModel {
    var delegate: FilterDelegate? = null

    abstract val key: String
    abstract val title: String
    abstract var list: List<FilterViewModel>
    abstract val refreshable: Boolean

    protected abstract val _state: MutableStateFlow<FilterState>
    val state
        get() = _state.asStateFlow()

    abstract fun onRefresh()

    open fun onAppear() {}
    open fun onClear() {}

    protected abstract val _refreshing: MutableStateFlow<Boolean>
    val refreshing
        get() = _refreshing.asStateFlow()

    fun setUpdatedFilters(list: List<FilterViewModel>) {
        this.list = list
        _state.value = FilterState.Content(list = this.list.map { it.copy() })
    }

    abstract fun onApply()

    fun onNavigateBack() {
        delegate?.onNavigateBack()
    }

    abstract fun copy(list: List<FilterViewModel>): FiltersViewModel

    fun copy(): FiltersViewModel = copy(list.map { it.copy() })

    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false

        other as FiltersViewModel

        if (key != other.key) return false
        if (title != other.title) return false
        if (list != other.list) return false

        return true
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}

interface FetchRemoteFilters {
    sealed class Response {
        data class Content(val list: List<FilterViewModel>) : Response()
        data class Error(val title: String, val message: String) : Response()
    }

    operator fun invoke(completion: (Response) -> Unit)
}

data class RemoteSingleChoiceFiltersViewModel(
    override val key: String,
    override val title: String,
    override var list: List<FilterViewModel> = emptyList(),
    private val scope: PlatformScopeManager,
    private val fetch: FetchRemoteFilters
) : FiltersViewModel() {
    override val refreshable: Boolean = true

    override val _state: MutableStateFlow<FilterState> = MutableStateFlow(FilterState.Loading)

    override val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private var selectedFilter: FilterViewModel? = list.firstOrNull { it.selected }

    override fun onAppear() {
        super.onAppear()
        _state.value = FilterState.Loading
        fetchList()
    }

    private fun fetchList() {
        fetch { response ->
            when (response) {
                is FetchRemoteFilters.Response.Content -> {
                    val newList = response.list.map {
                        if (selectedFilter != null) {
                            it.selected = it.id == selectedFilter?.id
                        }
                        it
                    }

                    if (newList.isNotEmpty()) {
                        _state.value = FilterState.Content(list = newList)
                    } else {
                        _state.value = FilterState.Empty(
                            title = "No items found",
                            message = "Feel free to refresh the list by tapping the button below",
                            onRetry = ::retry
                        )
                    }


                }
                is FetchRemoteFilters.Response.Error -> {
                    _state.value =
                        FilterState.Error(title = response.title, message = response.message, onRetry = ::retry)
                }
            }

        }
    }

    private fun retry() {
        _state.value = FilterState.Loading
        scope.cancelAllJobs()
        fetchList()
    }

    fun onSelect(filter: FilterViewModel) {
        val currentState: FilterState.Content = _state.value as? FilterState.Content ?: return

        selectedFilter = filter

        currentState.list.forEach {
            it.selected = it.id == filter.id
        }
    }

    override fun onApply() {
        val currentState: FilterState.Content = _state.value as? FilterState.Content ?: return

        val appliedList = mutableListOf<FilterViewModel>()

        val selected = currentState.list.firstOrNull { it.selected }

        selected?.let {
            appliedList.add(it)
        }

        delegate?.onApplied(list = appliedList)
    }

    override fun onRefresh() {
        if (!refreshable) {
            throw IllegalArgumentException("You are calling onRefresh method of filter that has disabled refresh functionality")
        }

        _refreshing.value = true
        scope.cancelAllJobs()
        fetchList()
    }

    override fun onClear() {
        super.onClear()
        scope.cancelAllJobs()
    }

    override fun copy(list: List<FilterViewModel>): FiltersViewModel {
        return RemoteSingleChoiceFiltersViewModel(
            key = this.key,
            fetch = this.fetch,
            title = this.title,
            scope = this.scope,
            list = list
        ).also {
            it._state.value = _state.value
        }
    }
}

data class RemoteFiltersViewModel(
    override val key: String,
    override val title: String,
    override var list: List<FilterViewModel> = emptyList(),
    private val scope: PlatformScopeManager,
    private val fetch: FetchRemoteFilters
) : FiltersViewModel() {
    override val refreshable: Boolean = true

    override val _state: MutableStateFlow<FilterState> = MutableStateFlow(FilterState.Loading)

    override val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private var _mutableList: List<FilterViewModel> = list.map { it.copy() }

    override fun onAppear() {
        super.onAppear()
        _state.value = FilterState.Loading
        fetchList()
    }

    private fun fetchList() {
        fetch { response ->
            when (response) {
                is FetchRemoteFilters.Response.Content -> {
                    _mutableList = response.list.map { newFilter ->
                        newFilter.selected = _mutableList.any { oldFilter ->
                            newFilter.id == oldFilter.id && oldFilter.selected
                        }
                        newFilter
                    }.toList()

                    if (_mutableList.isNotEmpty()) {
                        _state.value = FilterState.Content(list = _mutableList)
                    } else {
                        _state.value = FilterState.Empty(
                            title = "No items found",
                            message = "Feel free to refresh the list by tapping the button below",
                            onRetry = ::retry
                        )
                    }


                }
                is FetchRemoteFilters.Response.Error -> {
                    _state.value =
                        FilterState.Error(title = response.title, message = response.message, onRetry = ::retry)
                }
            }

        }
    }

    private fun retry() {
        _state.value = FilterState.Loading
        scope.cancelAllJobs()
        fetchList()
    }

    override fun onApply() {
        val currentFilters: FilterState.Content = _state.value as? FilterState.Content ?: return

        delegate?.onApplied(currentFilters.list.filter { it.selected }.map { it.copy() })
    }

    override fun onRefresh() {
        if (!refreshable) {
            throw IllegalArgumentException("You are calling onRefresh method of filter that has disabled refresh functionality")
        }

        _refreshing.value = true
        scope.cancelAllJobs()
        fetchList()
    }

    override fun onClear() {
        super.onClear()
        scope.cancelAllJobs()
    }

    override fun copy(list: List<FilterViewModel>): FiltersViewModel {
        return RemoteFiltersViewModel(
            key = this.key,
            fetch = this.fetch,
            title = this.title,
            scope = this.scope,
            list = list
        ).also {
            it._state.value = _state.value
        }
    }
}

data class FilterViewModel(
    val id: String,
    val name: String,
    private var _selected: Boolean = false
) {
    private val _state: MutableStateFlow<FilterData> =
        MutableStateFlow(FilterData(id = id, name = name, selected = _selected))
    val state: StateFlow<FilterData>
        get() = _state.asStateFlow()

    var selected: Boolean
        get() = _selected
        set(value) {
            _selected = value
            _state.update { _state.value.copy(selected = _selected) }
        }

    fun onToggle() {
        _selected = !_state.value.selected
        _state.update { _state.value.copy(selected = _selected) }
    }

    fun copy(): FilterViewModel = FilterViewModel(id = id, name = name, _selected = _selected)
}

data class FilterData(
    val id: String,
    val name: String,
    val selected: Boolean = false
)

sealed class FilterState {
    abstract fun copy(): FilterState

    object Loading : FilterState() {
        override fun copy(): Loading = Loading
    }

    data class Empty(val title: String, val message: String, val onRetry: (() -> Unit)? = null) : FilterState() {
        override fun copy(): Empty = Empty(title = title, message = message, onRetry = onRetry)
    }

    data class Error(val title: String, val message: String, val onRetry: (() -> Unit)? = null) : FilterState() {
        override fun copy(): Error = Error(title = title, message = message, onRetry = onRetry)
    }

    data class Content(val list: List<FilterViewModel>) : FilterState() {
        override fun copy(): Content = Content(list = list.map { it.copy() })
    }
}

interface FilterDelegate {
    fun onNavigateBack()
    fun onApplied(list: List<FilterViewModel>)
}

