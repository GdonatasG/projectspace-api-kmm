package com.project.space.feature.userinvitations.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.userinvitations.AcceptState
import com.project.space.feature.userinvitations.State
import com.project.space.feature.userinvitations.UserInvitationsPresenter
import com.project.space.feature.userinvitations.UserInvitationsView
import com.project.space.feature.userinvitations.domain.Invitation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserInvitationsViewModel(
    private val presenter: UserInvitationsPresenter
) : ViewModel(), UserInvitationsView, LifecycleEventObserver {
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState>
        get() = _state.asStateFlow()

    private val _acceptState: MutableStateFlow<AcceptViewState> = MutableStateFlow(AcceptViewState.Idle)
    val acceptState: StateFlow<AcceptViewState>
        get() = _acceptState.asStateFlow()

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean>
        get() = _refreshing.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> presenter.onResume()
            Lifecycle.Event.ON_STOP -> presenter.onDisappear()

            else -> {}
        }
    }

    override fun display(state: State) {
        state.mapToViewState()?.let { newState ->
            _state.update { newState }
        }
    }

    override fun display(state: AcceptState) {
        _acceptState.update { state.mapToViewState() }
    }

    fun onRetry() {
        presenter.onRetry()
    }

    fun onRefresh() {
        presenter.onRefresh()
    }

    fun onNavigateBack() {
        presenter.onNavigateBack()
    }

    fun onAccept(invitation: Invitation) {
        presenter.onAccept(invitation)
    }

    override fun onCleared() {
        super.onCleared()
        presenter.dropView()
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val data: List<Invitation>) : ViewState()
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

    sealed class AcceptViewState {
        object Idle : AcceptViewState()
        object Loading : AcceptViewState()
    }

    private fun AcceptState.mapToViewState(): AcceptViewState = when (this) {
        AcceptState.Idle -> AcceptViewState.Idle
        AcceptState.Loading -> AcceptViewState.Loading
    }
}
