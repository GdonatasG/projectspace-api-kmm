package com.project.space.feature.editprofile

actual interface EditProfileView {
    fun display(state: State)
    fun display(state: UpdateState)

    fun display(state: FormErrors)

}

internal actual fun update(view: EditProfileView?, state: State) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: EditProfileView?, state: UpdateState) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: EditProfileView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
