package com.project.space.feature.createproject

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.createproject.domain.CreateProject

class DefaultCreateProjectPresenter(
    private val scope: PlatformScopeManager,
    private val delegate: CreateProjectDelegate,
    private val alert: Alert.Coordinator,
    private val createProject: CreateProject
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
        formErrors = FormErrors.empty()

        if (name.trim().isBlank()) {
            formErrors = formErrors.copy(name = "Name must not be empty!")
        }

        if (formErrors.isValid()) {
            state = State.Loading

            createProject(
                name = name,
                description = description
            ) { response ->
                state = State.Idle

                when (response) {
                    is CreateProject.Response.Success -> {
                        alert.show(Alert {
                            title = "Project successfully created!"
                            buttons = listOf(Alert.Button {
                                title = "Ok"
                                event = Alert.Button.Event.DESTRUCTIVE
                                onClick = {}
                            })
                        })
                    }
                    is CreateProject.Response.InputErrors -> {
                        response.data.forEach { error ->
                            when (error.input) {
                                "entity" -> {
                                    alert.show(Alert {
                                        title = "Failed!"
                                        message = error.message
                                        buttons = listOf(Alert.Button {
                                            title = "Ok"
                                            event = Alert.Button.Event.DESTRUCTIVE
                                            onClick = {}
                                        })
                                    })
                                }
                                "name" -> {
                                    formErrors = formErrors.copy(name = error.message)
                                }
                            }
                        }
                    }
                    is CreateProject.Response.Error -> {
                        alert.show(Alert {
                            title = "Failed!"
                            message = response.message
                            buttons = listOf(Alert.Button {
                                title = "Ok"
                                event = Alert.Button.Event.DESTRUCTIVE
                                onClick = {}
                            })
                        })
                    }
                }

            }

        }
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }
}
