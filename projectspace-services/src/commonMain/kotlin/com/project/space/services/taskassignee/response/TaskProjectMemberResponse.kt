package com.project.space.services.taskassignee.response

import com.project.space.services.projectmemberlevel.response.ProjectMemberLevelResponse
import com.project.space.services.user.response.UserResponse
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaskProjectMemberResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("user")
    val user: UserResponse,
    @SerialName("level")
    val level: ProjectMemberLevelResponse
)
