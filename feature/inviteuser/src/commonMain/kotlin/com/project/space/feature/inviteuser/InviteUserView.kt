package com.project.space.feature.inviteuser

sealed class State {
    object Idle : State()
    object Loading : State()
}

data class FormErrors(
    val email: String? = null
) {
    companion object {
        fun empty(): FormErrors = FormErrors()
    }

    fun isValid(): Boolean =
        email == null
}

internal expect fun update(view: InviteUserView?, state: State)
internal expect fun update(view: InviteUserView?, state: FormErrors)

expect interface InviteUserView
