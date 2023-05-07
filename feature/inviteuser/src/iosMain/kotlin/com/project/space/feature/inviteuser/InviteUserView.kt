package com.project.space.feature.inviteuser

actual interface InviteUserView {
    fun display(editProfileViewIdle: State.Idle)
    fun display(editProfileViewLoading: State.Loading)

    fun display(editProfileViewFormErrors: FormErrors)
}

internal actual fun update(view: InviteUserView?, state: State) {
    view ?: return
    when (state) {
        is State.Idle -> view.display(state)
        is State.Loading -> view.display(state)
    }
}

internal actual fun update(view: InviteUserView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
