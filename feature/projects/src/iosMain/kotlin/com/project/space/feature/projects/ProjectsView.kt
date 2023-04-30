package com.project.space.feature.projects

actual interface ProjectsView {
    fun display(projectsViewViewStateLoading: State.Loading)
    fun display(projectsViewViewStateContent: State.Content)
    fun display(projectsViewViewStateEmpty: State.Empty)
    fun display(projectsViewViewStateError: State.Error)
}

internal actual fun update(view: ProjectsView?, state: State) {
    view ?: return
    when (state) {
        is State.Loading -> view.display(state)
        is State.Content -> view.display(state)
        is State.Empty -> view.display(state)
        is State.Error -> view.display(state)
    }
}
