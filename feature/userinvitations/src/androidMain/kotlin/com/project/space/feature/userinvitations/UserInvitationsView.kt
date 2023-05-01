package com.project.space.feature.userinvitations

actual interface UserInvitationsView {
    fun display(state: State)
    fun display(state: AcceptState)
}

internal actual fun update(view: UserInvitationsView?, state: State) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: UserInvitationsView?, state: AcceptState) {
    view ?: return
    view.display(state)
}
