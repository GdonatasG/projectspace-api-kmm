package com.project.space.services.task

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.common.response.SimpleSuccessResponse
import com.project.space.services.task.builder.CreateTask
import com.project.space.services.task.builder.GetProjectTasks
import com.project.space.services.task.builder.GetSessionUserAssignedTasks
import com.project.space.services.task.builder.GetTask
import com.project.space.services.task.response.TaskDataResponse
import com.project.space.services.task.response.TasksDataListResponse

class TaskService(private val client: ProjectSpaceHttpClient) {
    suspend fun getTask(id: Int): ProjectSpaceResult<TaskDataResponse> = client.request(GetTask(id = id))

    suspend fun getSessionUserAssignedTasks(projectId: Int): ProjectSpaceResult<TasksDataListResponse> =
        client.request(GetSessionUserAssignedTasks(projectId = projectId))

    suspend fun getProjectTasks(projectId: Int): ProjectSpaceResult<TasksDataListResponse> =
        client.request(GetProjectTasks(projectId = projectId))

    suspend fun createTask(
        projectId: Int,
        title: String,
        priorityId: Int,
        builder: CreateTask.() -> Unit = {}
    ): ProjectSpaceResult<SimpleSuccessResponse> =
        client.request(CreateTask(projectId = projectId, title = title, priorityId = priorityId).apply(builder))
}
