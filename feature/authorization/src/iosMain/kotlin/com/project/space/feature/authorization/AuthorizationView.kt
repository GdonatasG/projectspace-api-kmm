package com.project.space.feature.authorization

actual interface AuthorizationView {
    fun display(authorizationViewStateLoading: State.Loading)
    fun display(authorizationViewStateIdle: State.Idle)
    fun display(authorizationViewFormErrors: FormErrors)
    actual fun onChangeModeToLogin()
}

internal actual fun update(view: AuthorizationView?, state: State) {
    view ?: return
    when (state) {
        is State.Idle -> view.display(state)
        is State.Loading -> view.display(state)
    }
}

internal actual fun update(view: AuthorizationView?, state: FormErrors) {
    view ?: return
    view.display(state)
}

