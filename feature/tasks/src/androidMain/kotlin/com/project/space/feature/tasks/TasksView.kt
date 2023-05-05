package com.project.space.feature.tasks

actual interface TasksView {
    fun display(state: State)
}

internal actual fun update(view: TasksView?, state: State) {
    view ?: return
    view.display(state)
}
