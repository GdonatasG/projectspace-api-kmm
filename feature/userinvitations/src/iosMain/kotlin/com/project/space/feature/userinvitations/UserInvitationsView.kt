package com.project.space.feature.userinvitations

actual interface UserInvitationsView {
    fun display(userInvitationsViewStateLoading: State.Loading)
    fun display(userInvitationsViewStateRefreshing: State.Refreshing)
    fun display(userInvitationsViewStateContent: State.Content)
    fun display(userInvitationsViewStateEmpty: State.Empty)
    fun display(userInvitationsViewStateError: State.Error)

    fun display(userInvitationsViewAcceptStateIdle: AcceptState.Idle)
    fun display(userInvitationsViewAcceptStateLoading: AcceptState.Loading)
}

internal actual fun update(view: UserInvitationsView?, state: State) {
    view ?: return
    when (state) {
        is State.Loading -> view.display(state)
        is State.Content -> view.display(state)
        is State.Empty -> view.display(state)
        is State.Error -> view.display(state)
        is State.Refreshing -> view.display(state)
    }
}

internal actual fun update(view: UserInvitationsView?, state: AcceptState) {
    view ?: return
    when (state) {
        is AcceptState.Idle -> view.display(state)
        is AcceptState.Loading -> view.display(state)
    }
}
