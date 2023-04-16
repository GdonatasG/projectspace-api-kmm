package com.project.space.feature.authorization


sealed class State {
    object Idle : State()
    object Loading : State()
}

data class FormErrors(val usernameError: String? = null, val passwordError: String? = null) {
    companion object {
        fun empty(): FormErrors = FormErrors(usernameError = null, passwordError = null)
    }
}

internal expect fun update(view: AuthorizationView?, state: State)

internal expect fun update(view: AuthorizationView?, state: FormErrors)

expect interface AuthorizationView {
    fun onChangeModeToLogin()
}
