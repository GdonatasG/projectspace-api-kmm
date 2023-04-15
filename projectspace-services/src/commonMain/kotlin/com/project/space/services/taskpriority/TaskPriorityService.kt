package com.project.space.services.taskpriority

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.taskpriority.builder.GetTaskPriorities
import com.project.space.services.taskpriority.response.TaskPrioritiesDataListResponse

class TaskPriorityService(private val client: ProjectSpaceHttpClient) {
    suspend fun getTaskPriorities(): ProjectSpaceResult<TaskPrioritiesDataListResponse> =
        client.request(GetTaskPriorities())
}
