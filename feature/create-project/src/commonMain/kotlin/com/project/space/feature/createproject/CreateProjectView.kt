package com.project.space.feature.createproject

sealed class State {
    object Idle : State()
    object Loading : State()
}

data class FormErrors(
    val name: String? = null,
    val description: String = "",
) {
    companion object {
        fun empty(): FormErrors = FormErrors()
    }

    fun isValid(): Boolean =
        name == null
}

internal expect fun update(view: CreateProjectView?, state: State)

internal expect fun update(view: CreateProjectView?, state: FormErrors)


expect interface CreateProjectView
