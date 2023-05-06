package com.project.space.feature.createtask

actual interface CreateTaskView {
    fun display(createProjectViewStateIdle: State.Idle)
    fun display(createProjectViewStateLoading: State.Loading)

    fun display(createProjectViewPriorityStateNone: PriorityState.None)
    fun display(createProjectViewPriorityStateSelected: PriorityState.Selected)

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

internal actual fun update(view: CreateTaskView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
