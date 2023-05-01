package com.project.space.feature.profile

interface ProfileView {

    fun display(profileViewGeneralStateLoading: GeneralState.Loading)
    fun display(profileViewGeneralStateRefreshing: GeneralState.Refreshing)
    fun display(profileViewGeneralStateContent: GeneralState.Content)

    fun display(profileViewInvitationsCountStateLoading: InvitationsCountState.Loading)
    fun display(profileViewInvitationsCountStateRefreshing: InvitationsCountState.Refreshing)
    fun display(profileViewInvitationsCountStateContent: InvitationsCountState.Content)
    fun display(profileViewInvitationsCountStateError: InvitationsCountState.Error)
}

internal actual fun update(view: ProfileView?, state: GeneralState) {
    view ?: return
    when (state) {
        is GeneralState.Loading -> view.display(state)
        is GeneralState.Refreshing -> view.display(state)
        is GeneralState.Content -> view.display(state)
    }
}

internal actual fun update(view: ProfileView?, state: InvitationsCountState) {
    view ?: return
    when (state) {
        is InvitationsCountState.Loading -> view.display(state)
        is InvitationsCountState.Refreshing -> view.display(state)
        is InvitationsCountState.Content -> view.display(state)
        is InvitationsCountState.Error -> view.display(state)
    }
}
