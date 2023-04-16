package com.project.space.feature.authorization


sealed class State {
    object Idle : State()
    object Loading : State()
}

sealed class Mode {
    object Login : Mode()
    object Register : Mode()
}

data class FormErrors(val usernameError: String? = null, val passwordError: String? = null) {
    companion object {
        fun empty(): FormErrors = FormErrors(usernameError = null, passwordError = null)
    }
}

internal expect fun update(view: AuthorizationView?, state: State)
internal expect fun update(view: AuthorizationView?, state: Mode)

internal expect fun update(view: AuthorizationView?, state: FormErrors)

expect interface AuthorizationView {
}
