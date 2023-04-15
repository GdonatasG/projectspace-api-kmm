package com.project.space.services.task.response

import com.project.space.services.project.response.ProjectResponse
import com.project.space.services.taskassignee.response.TaskAssigneeResponse
import com.project.space.services.taskassignee.response.TaskProjectMemberResponse
import com.project.space.services.taskpriority.response.TaskPriorityResponse
import com.project.space.services.taskstatus.response.TaskStatusResponse
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaskResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("creator")
    val creator: TaskProjectMemberResponse,
    @SerialName("status")
    val status: TaskStatusResponse,
    @SerialName("priority")
    val priority: TaskPriorityResponse,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("end_date")
    val endDate: String? = null,
    @SerialName("project")
    val project: ProjectResponse,
    @SerialName("assignees")
    val assignees: List<TaskAssigneeResponse>
)
