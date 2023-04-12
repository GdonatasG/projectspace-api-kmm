package com.project.space.services.taskstatus

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.taskstatus.builder.GetTaskStatuses
import com.project.space.services.taskstatus.response.TaskStatusesDataListResponse

class TaskStatusService(private val client: ProjectSpaceHttpClient) {
    suspend fun getTaskStatuses(): ProjectSpaceResult<TaskStatusesDataListResponse> = client.request(GetTaskStatuses())
}
