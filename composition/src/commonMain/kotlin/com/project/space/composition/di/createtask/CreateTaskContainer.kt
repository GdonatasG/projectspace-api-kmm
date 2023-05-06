package com.project.space.composition.di.createtask

import com.libraries.alerts.Alert
import com.libraries.utils.DateTimeFormatter
import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.common.FetchRemoteFilters
import com.project.space.feature.common.FilterViewModel
import com.project.space.feature.common.RemoteSingleChoiceFiltersViewModel
import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.createtask.CreateTaskDelegate
import com.project.space.feature.createtask.CreateTaskPresenter
import com.project.space.feature.createtask.DefaultCreateTaskPresenter
import com.project.space.feature.createtask.domain.CreateTask
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.Timestamp
import com.project.space.services.task.TaskService
import com.project.space.services.taskpriority.TaskPriorityService
import com.project.space.services.taskpriority.response.TaskPriorityName

class CreateTaskContainer(
    private val taskService: TaskService,
    private val taskPriorityService: TaskPriorityService,
    private val selectedProjectManager: SelectedProjectManager
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val priorityFilterScope: PlatformScopeManager = PlatformScopeManager()

    private val createTaskUseCase: CreateTask by lazy {
        CreateTaskUseCase(
            scope = scope, taskService = taskService, selectedProjectManager = selectedProjectManager
        )
    }

    private val priorityFilterUseCase: FetchRemoteFilters by lazy {
        PriorityFilterUseCase(
            scope = scope,
            taskPriorityService = taskPriorityService
        )
    }

    fun presenter(alert: Alert.Coordinator, delegate: CreateTaskDelegate): CreateTaskPresenter =
        DefaultCreateTaskPresenter(
            scope = scope,
            delegate = delegate,
            alert = alert,
            createTask = createTaskUseCase,
            getSelectedProject = {
                selectedProjectManager.getSelectedProject()
            },
            priorityFilter = RemoteSingleChoiceFiltersViewModel(
                key = "priority_filter", title = "Priority", scope = priorityFilterScope, fetch = priorityFilterUseCase
            )
        )
}

private class PriorityFilterUseCase(
    private val scope: PlatformScopeManager,
    private val taskPriorityService: TaskPriorityService
) : FetchRemoteFilters {
    override fun invoke(completion: (FetchRemoteFilters.Response) -> Unit) {
        scope.launch {
            return@launch when (val response = taskPriorityService.getTaskPriorities()) {
                is ProjectSpaceResult.Success -> {
                    completion(FetchRemoteFilters.Response.Content(list = response.data.data.map {
                        FilterViewModel(
                            id = it.id.toString(),
                            name = when (it.name) {
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
                            title = "Unable to load priorities!",
                            message = response.error.errors[0].message
                        )
                    )
                }
                else -> {
                    completion(
                        FetchRemoteFilters.Response.Error(
                            title = "Unable to load priorities!",
                            message = "Something went wrong. Try again!"
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
        startDate: Double?,
        endDate: Double?,
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
                            formatter.timestampToUTC0Timestamp(timestamp = it).toLong()
                        )
                    )
                }
                endDate?.let {
                    endDate(
                        Timestamp(
                            formatter.timestampToUTC0Timestamp(timestamp = it).toLong()
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
