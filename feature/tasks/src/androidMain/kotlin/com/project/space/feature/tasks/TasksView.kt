package com.project.space.feature.tasks

actual interface TasksView {
    fun display(state: State)
    fun display(state: SelectedProjectState)
}

internal actual fun update(view: TasksView?, state: State) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: TasksView?, state: SelectedProjectState) {
    view ?: return
    view.display(state)
}
