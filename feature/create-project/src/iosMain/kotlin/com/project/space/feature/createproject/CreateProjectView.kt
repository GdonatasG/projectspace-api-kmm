package com.project.space.feature.createproject

actual interface CreateProjectView {
    fun display(createProjectViewStateIdle: State.Idle)
    fun display(createProjectViewStateLoading: State.Loading)

    fun display(createProjectViewFormErrors: FormErrors)
}

internal actual fun update(view: CreateProjectView?, state: State) {
    view ?: return
    when (state) {
        is State.Idle -> view.display(state)
        is State.Loading -> view.display(state)
    }
}

internal actual fun update(view: CreateProjectView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
