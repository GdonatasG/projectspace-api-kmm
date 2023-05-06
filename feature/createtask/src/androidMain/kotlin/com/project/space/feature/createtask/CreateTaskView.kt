package com.project.space.feature.createtask

actual interface CreateTaskView {
    fun display(state: State)
    fun display(state: PriorityState)

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

internal actual fun update(view: CreateTaskView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
