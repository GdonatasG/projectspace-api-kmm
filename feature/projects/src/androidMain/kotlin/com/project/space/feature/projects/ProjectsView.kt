package com.project.space.feature.projects

actual interface ProjectsView {
    fun display(state: State)
}

internal actual fun update(view: ProjectsView?, state: State) {
    view ?: return
    view.display(state)
}
