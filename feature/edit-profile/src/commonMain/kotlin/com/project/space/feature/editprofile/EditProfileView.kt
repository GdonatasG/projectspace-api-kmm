package com.project.space.feature.editprofile

import com.project.space.feature.editprofile.domain.CurrentUser

sealed class State {
    object Loading : State()
    data class Content(val user: CurrentUser) : State()
    data class Error(val title: String, val message: String) : State()
}

sealed class UpdateState {
    object Idle : UpdateState()
    object Loading : UpdateState()
}

data class FormErrors(
    val firstName: String? = null,
    val lastName: String? = null
) {
    companion object {
        fun empty(): FormErrors = FormErrors()
    }

    fun isValid(): Boolean =
        firstName == null && lastName == null
}

internal expect fun update(view: EditProfileView?, state: State)

internal expect fun update(view: EditProfileView?, state: UpdateState)

internal expect fun update(view: EditProfileView?, state: FormErrors)

expect interface EditProfileView
