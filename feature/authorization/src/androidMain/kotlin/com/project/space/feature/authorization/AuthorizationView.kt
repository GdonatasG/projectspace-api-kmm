package com.project.space.feature.authorization

actual interface AuthorizationView {
    fun display(state: State)
    fun display(state: FormErrors)
    actual fun onChangeModeToLogin()
}

internal actual fun update(view: AuthorizationView?, state: State) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: AuthorizationView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
