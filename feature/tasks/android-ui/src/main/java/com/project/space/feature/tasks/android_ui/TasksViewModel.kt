package com.project.space.feature.tasks.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.tasks.*
import com.project.space.feature.tasks.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TasksViewModel(
    private val presenter: TasksPresenter
) : ViewModel(), TasksView, LifecycleEventObserver {

    private val _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int>
        get() = _selectedTabIndex.asStateFlow()

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState>
        get() = _state.asStateFlow()

    private val _selectedProjectState: MutableStateFlow<SelectedProjectViewState> =
        MutableStateFlow(SelectedProjectViewState.None(title = "", message = ""))
    val selectedProjectState: StateFlow<SelectedProjectViewState>
        get() = _selectedProjectState.asStateFlow()

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean>
        get() = _refreshing.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        state.mapToViewState()?.let { newState ->
            _state.update { newState }
        }
    }

    override fun display(state: SelectedProjectState) {
        _selectedProjectState.update { state.mapToViewState() }
    }

    fun onTabChange(index: Int) {
        if (_selectedTabIndex.value == index) return

        _selectedTabIndex.update { index }
        presenter.onTabChange(
            if (index == 0) Tab.MY_TASKS
            else Tab.ALL_TASKS
        )
    }

    fun onRetry() {
        presenter.onRetry()
    }

    fun onRefresh() {
        presenter.onRefresh()
    }

    fun onNavigateToCreateTask() {
        presenter.onNavigateToCreateTask()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> presenter.onResume()
            Lifecycle.Event.ON_STOP -> presenter.onDisappear()

            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        presenter.dropView()
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val data: List<Task>) : ViewState()
        data class Empty(val title: String, val message: String) : ViewState()
        data class Error(val title: String, val message: String) : ViewState()
    }

    private fun State.mapToViewState(): ViewState? = when (this) {
        is State.Loading -> ViewState.Loading
        is State.Refreshing -> {
            _refreshing.update { true }
            null
        }
        is State.Content -> {
            _refreshing.update { false }
            ViewState.Content(data = this.data)
        }
        is State.Empty -> ViewState.Empty(title = this.title, message = this.message)
        is State.Error -> ViewState.Error(title = this.title, message = this.message)
    }

    sealed class SelectedProjectViewState {
        data class None(val title: String, val message: String) : SelectedProjectViewState()
        data class Selected(val project: SelectedProject) : SelectedProjectViewState()
    }

    private fun SelectedProjectState.mapToViewState(): SelectedProjectViewState = when (this) {
        is SelectedProjectState.None -> SelectedProjectViewState.None(title = this.title, message = this.message)
        is SelectedProjectState.Selected -> SelectedProjectViewState.Selected(project = this.project)
    }
}
