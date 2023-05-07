package com.project.space.feature.createtask

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.common.FilterDelegate
import com.project.space.feature.common.FilterViewModel
import com.project.space.feature.common.RemoteFiltersViewModel
import com.project.space.feature.common.RemoteSingleChoiceFiltersViewModel
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.createtask.domain.CreateTask

class DefaultCreateTaskPresenter(
    private val scope: PlatformScopeManager,
    private val delegate: CreateTaskDelegate,
    private val alert: Alert.Coordinator,
    private val createTask: CreateTask,
    private val getSelectedProject: (() -> SelectedProject?),
    private val priorityFilter: RemoteSingleChoiceFiltersViewModel,
    private val assigneesFilter: RemoteFiltersViewModel
) : CreateTaskPresenter() {
    override var viewHolder: ViewHolder<CreateTaskView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var state: State = State.Idle
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var priorityState: PriorityState = PriorityState.None
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var assigneesState: AssigneesState = AssigneesState(data = emptyList())
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var selectedProjectState: SelectedProjectState = SelectedProjectState.None(
        title = "No project selected!",
        message = "You do not have selected any project, feel free to select a project in Projects section below."
    )
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var formErrors: FormErrors = FormErrors.empty()
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    override fun onResume() {
        super.onResume()
        val newSelectedProject = getSelectedProject()

        val currentSelectedProject: SelectedProject? = (selectedProjectState as? SelectedProjectState.Selected)?.project

        if (currentSelectedProject == newSelectedProject) return

        if (newSelectedProject != null) {
            selectedProjectState = SelectedProjectState.Selected(project = newSelectedProject)
            return
        }

        selectedProjectState = SelectedProjectState.None(
            title = "No project selected!",
            message = "You do not have selected any project, feel free to select a project in Projects section below."
        )
    }

    override fun onNavigateBack() {
        delegate.onNavigateBack()
    }

    override fun onNavigateToPrioritySelection() {
        val filter = priorityFilter.copy()

        filter.delegate = object : FilterDelegate {
            override fun onNavigateBack() {
                delegate.onNavigateBack()
            }

            override fun onApplied(list: List<FilterViewModel>) {
                priorityFilter.setUpdatedFilters(list)
                val selectedFilter = list.firstOrNull { it.selected }
                selectedFilter?.let {
                    priorityState = PriorityState.Selected(
                        priority = Priority(
                            id = it.id.toInt(), name = it.name
                        )
                    )
                }
                delegate.onNavigateBack()
            }
        }

        delegate.onNavigateToFilter(filter)
    }

    override fun onNavigateToAssigneesSelection() {
        val filter = assigneesFilter.copy()

        filter.delegate = object : FilterDelegate {
            override fun onNavigateBack() {
                delegate.onNavigateBack()
            }

            override fun onApplied(list: List<FilterViewModel>) {
                assigneesFilter.setUpdatedFilters(list)
                assigneesState = AssigneesState(data = list.filter { it.selected }.map {
                    Assignee(id = it.id.toInt(), name = it.name)
                })

                delegate.onNavigateBack()
            }
        }

        delegate.onNavigateToFilter(filter)

    }

    override fun onCreateTask(title: String, description: String, startDate: String?, endDate: String?) {
        formErrors = FormErrors.empty()

        val taskTitle: String = title.trim()
        val taskPriority: Priority? = (priorityState as? PriorityState.Selected)?.priority
        val taskDescription: String? = description.takeIf { it.trim().isNotBlank() }

        if (taskTitle.isBlank()) {
            formErrors = formErrors.copy(title = "Title must not be empty!")
        }

        if (taskPriority == null) {
            formErrors = formErrors.copy(
                priority = "Priority must be selected!"
            )
        }

        if (formErrors.isValid()) {
            state = State.Loading

            createTask(
                title = taskTitle,
                priorityId = taskPriority?.id ?: -1,
                description = taskDescription,
                startDate = startDate,
                endDate = endDate,
                assignees = assigneesState.data.map { it.id }
            ) { response ->
                state = State.Idle
                when (response) {
                    is CreateTask.Response.Success -> {
                        alert.show(Alert {
                            this.title = "Task successfully created!"
                            buttons = listOf(Alert.Button {
                                this.title = "Ok"
                                event = Alert.Button.Event.DESTRUCTIVE
                                onClick = {}
                            })
                        })
                    }
                    is CreateTask.Response.InputErrors -> {
                        response.data.forEach { error ->
                            when (error.input) {
                                "entity" -> {
                                    alert.show(Alert {
                                        this.title = "Failed!"
                                        message = error.message
                                        buttons = listOf(Alert.Button {
                                            this.title = "Ok"
                                            event = Alert.Button.Event.DESTRUCTIVE
                                            onClick = {}
                                        })
                                    })
                                }
                                "title" -> {
                                    formErrors = formErrors.copy(title = error.message)
                                }
                                "priority_id" -> {
                                    formErrors = formErrors.copy(priority = error.message)
                                }
                            }
                        }
                    }
                    is CreateTask.Response.Error -> {
                        alert.show(Alert {
                            this.title = "Failed!"
                            message = response.message
                            buttons = listOf(Alert.Button {
                                this.title = "Ok"
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
