package com.project.space.feature.createproject

actual interface CreateProjectView {
    fun display(state: State)

    fun display(state: FormErrors)

}

internal actual fun update(view: CreateProjectView?, state: State) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: CreateProjectView?, state: FormErrors) {
    view ?: return
    view.display(state)
}
