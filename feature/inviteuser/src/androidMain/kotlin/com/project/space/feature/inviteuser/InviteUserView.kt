package com.project.space.feature.inviteuser

actual interface InviteUserView {
    fun display(state: State)
    fun display(state: FormErrors)
}

internal actual fun update(view: InviteUserView?, state: State) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: InviteUserView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
