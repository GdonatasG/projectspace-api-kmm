package com.project.space.feature.profile

import com.project.space.feature.common.domain.model.CurrentUser

sealed class State {
    object Loading : State()
    object Refreshing : State()
    object Content : State()
}

sealed class GeneralState {
    object Loading : GeneralState()
    object Refreshing : GeneralState()
    data class Content(val user: CurrentUser?) : GeneralState()
}

sealed class InvitationsCountState {
    object Loading : InvitationsCountState()
    object Refreshing : InvitationsCountState()
    data class Content(val count: Int) : InvitationsCountState()
    data class Error(val title: String, val message: String) : InvitationsCountState()
}

internal expect fun update(view: ProfileView?, state: GeneralState)
internal expect fun update(view: ProfileView?, state: InvitationsCountState)

expect interface ProfileView
