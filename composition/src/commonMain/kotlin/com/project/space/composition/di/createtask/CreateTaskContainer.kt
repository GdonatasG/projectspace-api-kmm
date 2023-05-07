package com.project.space.composition.di.createtask

import com.libraries.alerts.Alert
import com.libraries.utils.DateTimeFormatter
import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.common.*
import com.project.space.feature.createtask.CreateTaskDelegate
import com.project.space.feature.createtask.CreateTaskPresenter
import com.project.space.feature.createtask.DefaultCreateTaskPresenter
import com.project.space.feature.createtask.domain.CreateTask
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.Timestamp
import com.project.space.services.projectmember.ProjectMemberService
import com.project.space.services.task.TaskService
import com.project.space.services.taskpriority.TaskPriorityService
import com.project.space.services.taskpriority.response.TaskPriorityName

class CreateTaskContainer(
    private val taskService: TaskService,
    private val taskPriorityService: TaskPriorityService,
    private val projectMemberService: ProjectMemberService,
    private val selectedProjectManager: SelectedProjectManager
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val createTaskUseCase: CreateTask by lazy {
        CreateTaskUseCase(
            scope = scope, taskService = taskService, selectedProjectManager = selectedProjectManager
        )
    }

    private val priorityFilterScope: PlatformScopeManager = PlatformScopeManager()
    private val priorityFilterUseCase: FetchRemoteFilters by lazy {
        PriorityFilterUseCase(
            scope = priorityFilterScope, taskPriorityService = taskPriorityService
        )
    }

    private val assigneesFilterScope: PlatformScopeManager = PlatformScopeManager()
    private val assigneesFilterUseCase: FetchRemoteFilters by lazy {
        AssigneesFilterUseCase(
            scope = assigneesFilterScope,
            projectMemberService = projectMemberService,
            selectedProjectManager = selectedProjectManager
        )
    }


    fun presenter(alert: Alert.Coordinator, delegate: CreateTaskDelegate): CreateTaskPresenter =
        DefaultCreateTaskPresenter(
            scope = scope, delegate = delegate, alert = alert, createTask = createTaskUseCase, getSelectedProject = {
                selectedProjectManager.getSelectedProject()
            }, priorityFilter = RemoteSingleChoiceFiltersViewModel(
                key = "priority_filter", title = "Priority", scope = priorityFilterScope, fetch = priorityFilterUseCase
            ), assigneesFilter = RemoteFiltersViewModel(
                key = "assignees_filter",
                title = "Assignees",
                scope = assigneesFilterScope,
                fetch = assigneesFilterUseCase
            )
        )
}

private class PriorityFilterUseCase(
    private val scope: PlatformScopeManager, private val taskPriorityService: TaskPriorityService
) : FetchRemoteFilters {
    override fun invoke(completion: (FetchRemoteFilters.Response) -> Unit) {
        scope.launch {
            return@launch when (val response = taskPriorityService.getTaskPriorities()) {
                is ProjectSpaceResult.Success -> {
                    completion(FetchRemoteFilters.Response.Content(list = response.data.data.map {
                        FilterViewModel(
                            id = it.id.toString(), name = when (it.name) {
                                TaskPriorityName.NORMAL -> "NORMAL"
                                TaskPriorityName.HIGH -> "HIGH"
                                TaskPriorityName.URGENT -> "URGENT"
                            }
                        )
                    }))
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        FetchRemoteFilters.Response.Error(
                            title = "Unable to load priorities!", message = response.error.errors[0].message
                        )
                    )
                }
                else -> {
                    completion(
                        FetchRemoteFilters.Response.Error(
                            title = "Unable to load priorities!", message = "Something went wrong. Try again!"
                        )
                    )
                }
            }
        }
    }
}

private class AssigneesFilterUseCase(
    private val scope: PlatformScopeManager,
    private val projectMemberService: ProjectMemberService,
    private val selectedProjectManager: SelectedProjectManager
) : FetchRemoteFilters {
    override fun invoke(completion: (FetchRemoteFilters.Response) -> Unit) {
        val projectId: Int = selectedProjectManager.getSelectedProject()?.id ?: return

        scope.launch {
            return@launch when (val response = projectMemberService.getProjectMembers(projectId = projectId)) {
                is ProjectSpaceResult.Success -> {
                    completion(FetchRemoteFilters.Response.Content(list = response.data.data.map {
                        FilterViewModel(
                            id = it.id.toString(), name = it.user.username
                        )
                    }))
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        FetchRemoteFilters.Response.Error(
                            title = "Unable to load project members!", message = response.error.errors[0].message
                        )
                    )
                }
                else -> {
                    completion(
                        FetchRemoteFilters.Response.Error(
                            title = "Unable to load project members!", message = "Something went wrong. Try again!"
                        )
                    )
                }
            }
        }
    }

}

private class CreateTaskUseCase(
    private val scope: PlatformScopeManager,
    private val taskService: TaskService,
    private val selectedProjectManager: SelectedProjectManager
) : CreateTask {
    override fun invoke(
        title: String,
        priorityId: Int,
        description: String?,
        startDate: String?,
        endDate: String?,
        assignees: List<Int>,
        completion: (CreateTask.Response) -> Unit
    ) {
        val projectId: Int = selectedProjectManager.getSelectedProject()?.id ?: return

        val formatter = DateTimeFormatter.shared

        scope.launch {
            return@launch when (val response = taskService.createTask(
                projectId = projectId, title = title, priorityId = priorityId
            ) {
                description?.let {
                    this.description(it)
                }
                startDate?.let {
                    startDate(
                        Timestamp(
                            formatter.stringToUTC0Timestamp(date = it, pattern = "yyyy-MM-dd'T'HH:mm:ss")
                        )
                    )
                }
                endDate?.let {
                    endDate(
                        Timestamp(
                            formatter.stringToUTC0Timestamp(date = it, pattern = "yyyy-MM-dd'T'HH:mm:ss")
                        )
                    )
                }
                assignees(assignees.toSet())
            }) {
                is ProjectSpaceResult.Success -> {
                    completion(CreateTask.Response.Success)
                }
                is ProjectSpaceResult.Error -> {
                    completion(CreateTask.Response.InputErrors(data = response.error.errors.map {
                        CreateTask.InputError(
                            input = it.type, message = it.message
                        )
                    }))
                }
                else -> {
                    completion(CreateTask.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }
    }

}
