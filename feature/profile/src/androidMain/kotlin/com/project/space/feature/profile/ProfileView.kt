package com.project.space.feature.profile

actual interface ProfileView {
    fun display(state: GeneralState)
    fun display(state: InvitationsCountState)
}

internal actual fun update(view: ProfileView?, state: GeneralState) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: ProfileView?, state: InvitationsCountState) {
    view ?: return
    view.display(state)
}
