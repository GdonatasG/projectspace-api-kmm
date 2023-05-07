package com.project.space.feature.createtask

actual interface CreateTaskView {
    fun display(state: State)
    fun display(state: PriorityState)
    fun display(state: AssigneesState)
    fun display(state: SelectedProjectState)

    fun display(state: FormErrors)

}

internal actual fun update(view: CreateTaskView?, state: State) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: CreateTaskView?, state: PriorityState) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: CreateTaskView?, state: AssigneesState) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: CreateTaskView?, state: SelectedProjectState) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: CreateTaskView?, state: FormErrors) {
    view ?: return
    view.display(state)
}

