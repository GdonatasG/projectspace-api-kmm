package com.project.space.feature.projects

actual interface ProjectsView {
    fun display(projectsViewStateLoading: State.Loading)
    fun display(projectsViewStateRefreshing: State.Refreshing)
    fun display(projectsViewStateContent: State.Content)
    fun display(projectsViewStateEmpty: State.Empty)
    fun display(projectsViewStateError: State.Error)
}

internal actual fun update(view: ProjectsView?, state: State) {
    view ?: return
    when (state) {
        is State.Loading -> view.display(state)
        is State.Content -> view.display(state)
        is State.Empty -> view.display(state)
        is State.Error -> view.display(state)
        is State.Refreshing -> view.display(state)
    }
}
