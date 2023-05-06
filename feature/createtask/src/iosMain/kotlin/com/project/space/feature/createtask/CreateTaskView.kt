package com.project.space.feature.createtask

import com.project.space.feature.common.domain.model.SelectedProject

actual interface CreateTaskView {
    fun display(createProjectViewStateIdle: State.Idle)
    fun display(createProjectViewStateLoading: State.Loading)

    fun display(createProjectViewPriorityStateNone: PriorityState.None)
    fun display(createProjectViewPriorityStateSelected: PriorityState.Selected)

    fun display(createProjectViewSelectedProjectStateNone: SelectedProjectState.None)
    fun display(createProjectViewSelectedProjectStateSelected: SelectedProjectState.Selected)

    fun display(createProjectViewFormErrors: FormErrors)
}

internal actual fun update(view: CreateTaskView?, state: State) {
    view ?: return
    when (state) {
        is State.Idle -> view.display(state)
        is State.Loading -> view.display(state)
    }
}

internal actual fun update(view: CreateTaskView?, state: PriorityState) {
    view ?: return
    when (state) {
        is PriorityState.None -> view.display(state)
        is PriorityState.Selected -> view.display(state)
    }
}

internal actual fun update(view: CreateTaskView?, state: SelectedProjectState) {
    view ?: return
    when (state) {
        is SelectedProjectState.None -> view.display(state)
        is SelectedProjectState.Selected -> view.display(state)
    }
}

internal actual fun update(view: CreateTaskView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
