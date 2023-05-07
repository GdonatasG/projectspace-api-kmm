package com.project.space.feature.dashboard.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.dashboard.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    private val presenter: DashboardPresenter
) : ViewModel(), DashboardView, LifecycleEventObserver {
    private val _statisticsViewState: MutableStateFlow<StatisticsViewState> =
        MutableStateFlow(StatisticsViewState.Loading)
    val statisticsViewState: StateFlow<StatisticsViewState>
        get() = _statisticsViewState.asStateFlow()

    private val _statisticsRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val statisticsRefreshing: StateFlow<Boolean>
        get() = _statisticsRefreshing.asStateFlow()

    private val _riskViewState: MutableStateFlow<RiskViewState> =
        MutableStateFlow(RiskViewState.Loading)
    val riskViewState: StateFlow<RiskViewState>
        get() = _riskViewState.asStateFlow()

    private val _riskRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val riskRefreshing: StateFlow<Boolean>
        get() = _riskRefreshing.asStateFlow()

    private val _membersViewState: MutableStateFlow<MembersViewState> =
        MutableStateFlow(MembersViewState.Loading)
    val membersViewState: StateFlow<MembersViewState>
        get() = _membersViewState.asStateFlow()

    private val _membersRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val membersRefreshing: StateFlow<Boolean>
        get() = _membersRefreshing.asStateFlow()

    private val _selectedProjectState: MutableStateFlow<SelectedProjectViewState> =
        MutableStateFlow(SelectedProjectViewState.None(title = "", message = ""))
    val selectedProjectState: StateFlow<SelectedProjectViewState>
        get() = _selectedProjectState.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }


    override fun display(state: StatisticsState) {
        state.mapToViewState()?.let { newState ->
            _statisticsViewState.update { newState }
        }
    }

    override fun display(state: RiskState) {
        state.mapToViewState()?.let { newState ->
            _riskViewState.update { newState }
        }
    }

    override fun display(state: MembersState) {
        state.mapToViewState()?.let { newState ->
            _membersViewState.update { newState }
        }
    }

    override fun display(state: SelectedProjectState) {
        _selectedProjectState.update { state.mapToViewState() }
    }

    fun onRefresh() {
        presenter.onRefresh()
    }

    fun onNavigateToNewInvitation() {
        presenter.onNavigateToNewInvitation()
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

    sealed class StatisticsViewState {
        object Loading : StatisticsViewState()
        data class Content(val data: Statistics) : StatisticsViewState()
        data class Error(val title: String, val message: String) : StatisticsViewState()
    }

    private fun StatisticsState.mapToViewState(): StatisticsViewState? {
        if (this is StatisticsState.Refreshing) {
            _statisticsRefreshing.update { true }
            return null
        }

        _statisticsRefreshing.update { false }

        return when (this) {
            is StatisticsState.Loading -> StatisticsViewState.Loading
            is StatisticsState.Content -> StatisticsViewState.Content(data = this.data)
            is StatisticsState.Error -> StatisticsViewState.Error(title = this.title, message = this.message)
            else -> null
        }
    }

    sealed class RiskViewState {
        object Loading : RiskViewState()
        data class Content(val data: Risk) : RiskViewState()
        data class Error(val title: String, val message: String) : RiskViewState()
    }

    private fun RiskState.mapToViewState(): RiskViewState? {
        if (this is RiskState.Refreshing) {
            _riskRefreshing.update { true }
            return null
        }

        _riskRefreshing.update { false }

        return when (this) {
            is RiskState.Loading -> RiskViewState.Loading
            is RiskState.Content -> RiskViewState.Content(data = this.data)
            is RiskState.Error -> RiskViewState.Error(title = this.title, message = this.message)
            else -> null
        }
    }

    sealed class MembersViewState {
        object Loading : MembersViewState()
        data class Content(val data: List<Member>) : MembersViewState()
        data class Error(val title: String, val message: String) : MembersViewState()
    }

    private fun MembersState.mapToViewState(): MembersViewState? {
        if (this is MembersState.Refreshing) {
            _membersRefreshing.update { true }
            return null
        }

        _membersRefreshing.update { false }

        return when (this) {
            is MembersState.Loading -> MembersViewState.Loading
            is MembersState.Content -> MembersViewState.Content(data = this.data)
            is MembersState.Error -> MembersViewState.Error(title = this.title, message = this.message)
            else -> null
        }
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
