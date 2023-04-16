package com.project.space.feature.authorization

actual interface AuthorizationView {
    fun display(authorizationViewStateLoading: State.Loading)
    fun display(authorizationViewStateIdle: State.Idle)

    fun display(authorizationViewModeLogin: Mode.Login)
    fun display(authorizationViewModeRegister: Mode.Register)

    fun display(authorizationViewFormErrors: FormErrors)
}

internal actual fun update(view: AuthorizationView?, state: State) {
    view ?: return
    when (state) {
        is State.Idle -> view.display(state)
        is State.Loading -> view.display(state)
    }
}

internal actual fun update(view: AuthorizationView?, state: Mode) {
    view ?: return
    when (state) {
        is Mode.Login -> view.display(state)
        is Mode.Register -> view.display(state)
    }
}

internal actual fun update(view: AuthorizationView?, state: FormErrors) {
    view ?: return
    view.display(state)
}

