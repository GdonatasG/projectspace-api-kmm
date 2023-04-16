package com.project.space.feature.authorization


sealed class State {
    object Idle : State()
    object Loading : State()
}

data class FormErrors(
    val usernameError: String? = null,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val passwordRepeatError: String? = null
) {
    companion object {
        fun empty(): FormErrors = FormErrors()
    }

    fun isValid(): Boolean =
        usernameError == null
                && firstNameError == null
                && lastNameError == null
                && emailError == null
                && passwordError == null
                && passwordRepeatError == null
}

internal expect fun update(view: AuthorizationView?, state: State)

internal expect fun update(view: AuthorizationView?, state: FormErrors)

expect interface AuthorizationView {
    fun onChangeModeToLogin()
}
