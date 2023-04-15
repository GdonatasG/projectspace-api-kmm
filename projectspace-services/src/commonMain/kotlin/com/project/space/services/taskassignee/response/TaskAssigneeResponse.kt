package com.project.space.services.taskassignee.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaskAssigneeResponse(
    @SerialName("project_member")
    val projectMember: TaskProjectMemberResponse
)
