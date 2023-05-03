package com.project.space.feature.editprofile

actual interface EditProfileView {
    fun display(editProfileViewStateLoading: State.Loading)
    fun display(editProfileViewStateContent: State.Content)
    fun display(editProfileViewStateError: State.Error)

    fun display(editProfileViewUpdateStateIdle: UpdateState.Idle)
    fun display(editProfileViewUpdateStateLoading: UpdateState.Loading)

    fun display(editProfileViewFormErrors: FormErrors)
}

internal actual fun update(view: EditProfileView?, state: State) {
    view ?: return
    when (state) {
        is State.Loading -> view.display(state)
        is State.Content -> view.display(state)
        is State.Error -> view.display(state)
    }
}

internal actual fun update(view: EditProfileView?, state: UpdateState) {
    view ?: return
    when (state) {
        is UpdateState.Idle -> view.display(state)
        is UpdateState.Loading -> view.display(state)
    }
}

internal actual fun update(view: EditProfileView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
