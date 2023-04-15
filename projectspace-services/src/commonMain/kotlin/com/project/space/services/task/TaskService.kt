package com.project.space.services.task

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.task.builder.GetTask
import com.project.space.services.task.response.TaskDataResponse

class TaskService(private val client: ProjectSpaceHttpClient) {
    suspend fun getTask(id: Int): ProjectSpaceResult<TaskDataResponse> = client.request(GetTask(id = id))
}
