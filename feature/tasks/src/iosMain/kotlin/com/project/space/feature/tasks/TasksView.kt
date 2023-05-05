package com.project.space.feature.tasks

actual interface TasksView {
    fun display(tasksViewStateLoading: State.Loading)
    fun display(tasksViewStateRefreshing: State.Refreshing)
    fun display(tasksViewStateContent: State.Content)
    fun display(tasksViewStateEmpty: State.Empty)
    fun display(tasksViewStateError: State.Error)
}

internal actual fun update(view: TasksView?, state: State) {
    view ?: return
    when (state) {
        is State.Loading -> view.display(state)
        is State.Content -> view.display(state)
        is State.Empty -> view.display(state)
        is State.Error -> view.display(state)
        is State.Refreshing -> view.display(state)
    }
}
