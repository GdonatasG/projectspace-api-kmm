package com.project.space.feature.authorization

actual interface AuthorizationView {
    fun display(state: State)
    fun display(state: Mode)
    fun display(state: FormErrors)
}

internal actual fun update(view: AuthorizationView?, state: State) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: AuthorizationView?, state: Mode) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: AuthorizationView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
