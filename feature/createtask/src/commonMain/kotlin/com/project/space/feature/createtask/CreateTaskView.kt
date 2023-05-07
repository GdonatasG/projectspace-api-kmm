package com.project.space.feature.createtask

import com.project.space.feature.common.domain.model.SelectedProject

sealed class State {
    object Idle : State()
    object Loading : State()
}

sealed class PriorityState {
    object None : PriorityState()
    data class Selected(val priority: Priority) : PriorityState()
}

data class AssigneesState(val data: List<Assignee>)

sealed class SelectedProjectState {
    data class None(val title: String, val message: String) : SelectedProjectState()
    data class Selected(val project: SelectedProject) : SelectedProjectState()
}

data class Priority(
    val id: Int, val name: String
)

data class Assignee(
    val id: Int, val name: String
)

data class FormErrors(
    val title: String? = null, val priority: String? = null
) {
    companion object {
        fun empty(): FormErrors = FormErrors()
    }

    fun isValid(): Boolean = title == null && priority == null
}

internal expect fun update(view: CreateTaskView?, state: State)

internal expect fun update(view: CreateTaskView?, state: PriorityState)
internal expect fun update(view: CreateTaskView?, state: AssigneesState)

internal expect fun update(view: CreateTaskView?, state: FormErrors)
internal expect fun update(view: CreateTaskView?, state: SelectedProjectState)


expect interface CreateTaskView
