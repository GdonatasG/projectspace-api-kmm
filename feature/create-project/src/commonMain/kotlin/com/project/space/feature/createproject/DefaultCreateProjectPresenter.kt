package com.project.space.feature.createproject

import com.libraries.utils.ViewHolder

class DefaultCreateProjectPresenter(
    private val delegate: CreateProjectDelegate
) : CreateProjectPresenter() {
    override var viewHolder: ViewHolder<CreateProjectView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var state: State = State.Idle
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var formErrors: FormErrors = FormErrors.empty()
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    override fun onNavigateBack() {
        delegate.onNavigateBack()
    }

    override fun onCreateProject(name: String, description: String) {

    }
}
