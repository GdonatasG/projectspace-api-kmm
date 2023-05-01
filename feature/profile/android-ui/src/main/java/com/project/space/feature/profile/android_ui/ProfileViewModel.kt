package com.project.space.feature.profile.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.common.domain.model.CurrentUser
import com.project.space.feature.profile.GeneralState
import com.project.space.feature.profile.InvitationsCountState
import com.project.space.feature.profile.ProfilePresenter
import com.project.space.feature.profile.ProfileView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val presenter: ProfilePresenter
) : ViewModel(), ProfileView, LifecycleEventObserver {

    private val _generalState: MutableStateFlow<GeneralViewState> = MutableStateFlow(GeneralViewState.Loading)
    val generalState: StateFlow<GeneralViewState>
        get() = _generalState.asStateFlow()

    private val _refreshingGeneral: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshingGeneral: StateFlow<Boolean>
        get() = _refreshingGeneral.asStateFlow()

    private val _invitationsState: MutableStateFlow<InvitationsViewState> =
        MutableStateFlow(InvitationsViewState.Loading)
    val invitationsState: StateFlow<InvitationsViewState>
        get() = _invitationsState.asStateFlow()

    private val _refreshingInvitations: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshingInvitations: StateFlow<Boolean>
        get() = _refreshingInvitations.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: GeneralState) {
        state.mapToViewState()?.let { newState ->
            _generalState.update { newState }
        }
    }

    override fun display(state: InvitationsCountState) {
        state.mapToViewState()?.let { newState ->
            _invitationsState.update { newState }
        }
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

    sealed class GeneralViewState {
        object Loading : GeneralViewState()
        data class Content(val user: CurrentUser?) : GeneralViewState()
    }

    private fun GeneralState.mapToViewState(): GeneralViewState? {
        if (this is GeneralState.Refreshing) {
            _refreshingGeneral.update { true }
        } else {
            _refreshingGeneral.update { false }
        }

        return when (this) {
            is GeneralState.Loading -> GeneralViewState.Loading
            is GeneralState.Content -> GeneralViewState.Content(user = this.user)
            else -> null
        }
    }

    sealed class InvitationsViewState {
        object Loading : InvitationsViewState()
        data class Content(val count: Int) : InvitationsViewState()
    }

    private fun InvitationsCountState.mapToViewState(): InvitationsViewState? {
        if (this is InvitationsCountState.Refreshing) {
            _refreshingInvitations.update { true }
        } else {
            _refreshingInvitations.update { false }
        }

        return when (this) {
            is InvitationsCountState.Loading -> InvitationsViewState.Loading
            is InvitationsCountState.Content -> InvitationsViewState.Content(count = this.count)
            else -> null
        }
    }

}
