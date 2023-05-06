package com.project.space.feature.createtask

sealed class State {
    object Idle : State()
    object Loading : State()
}

sealed class PriorityState {
    object None : PriorityState()
    data class Selected(val priority: Priority) : PriorityState()
}

data class Priority(
    val id: Int,
    val name: String
)

data class FormErrors(
    val title: String? = null,
    val priority: String? = null
) {
    companion object {
        fun empty(): FormErrors = FormErrors()
    }

    fun isValid(): Boolean =
        title == null && priority == null
}

internal expect fun update(view: CreateTaskView?, state: State)

internal expect fun update(view: CreateTaskView?, state: PriorityState)

internal expect fun update(view: CreateTaskView?, state: FormErrors)

expect interface CreateTaskView
