package com.project.space.composition.di.tasks.usecase

import com.libraries.utils.DateTimeFormatter
import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.tasks.Tab
import com.project.space.feature.tasks.domain.GetTasks
import com.project.space.feature.tasks.domain.Task
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.task.TaskService
import com.project.space.services.task.response.TaskResponse

class GetTasksUseCase(
    private val scope: PlatformScopeManager,
    private val selectedProjectManager: SelectedProjectManager,
    private val taskService: TaskService
) : GetTasks {
    override fun invoke(tab: Tab, completion: (GetTasks.Response) -> Unit) {
        val selectedProject = selectedProjectManager.getSelectedProject() ?: return

        scope.launch {
            return@launch when (val tasksResponse =
                if (tab == Tab.MY_TASKS) taskService.getSessionUserAssignedTasks(projectId = selectedProject.id)
                else taskService.getProjectTasks(projectId = selectedProject.id)) {
                is ProjectSpaceResult.Success -> {
                    completion(GetTasks.Response.Success(data = tasksResponse.data.data.map { it.toDomain() }))
                }
                is ProjectSpaceResult.Error -> {
                    completion(GetTasks.Response.Error(message = tasksResponse.error.errors[0].message))
                }
                else -> {
                    completion(GetTasks.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }
    }
}

private fun TaskResponse.toDomain(): Task {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss"
    val formatter = DateTimeFormatter.shared

    var startDate: String? = null
    var endDate: String? = null

    this.endDate?.let {
        endDate = formatter.localTimezoneConverted(date = it, fromPattern = pattern, toPattern = "MMM dd")
    }

    this.startDate?.let {
        startDate = formatter.localTimezoneConverted(date = it, fromPattern = pattern, toPattern = "MMM dd")
    }

    return Task(
        id = this.id,
        title = this.title,
        description = this.description ?: "",
        startDate = startDate,
        endDate = endDate
    )
}
