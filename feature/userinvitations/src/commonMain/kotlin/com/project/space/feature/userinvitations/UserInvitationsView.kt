package com.project.space.feature.userinvitations

import com.project.space.feature.userinvitations.domain.Invitation

sealed class State {
    object Loading : State()
    object Refreshing : State()
    data class Content(val data: List<Invitation>) : State()
    data class Empty(val title: String, val message: String) : State()
    data class Error(val title: String, val message: String) : State()
}

sealed class AcceptState {
    object Idle: AcceptState()
    object Loading: AcceptState()
}

internal expect fun update(view: UserInvitationsView?, state: State)
internal expect fun update(view: UserInvitationsView?, state: AcceptState)

expect interface UserInvitationsView
