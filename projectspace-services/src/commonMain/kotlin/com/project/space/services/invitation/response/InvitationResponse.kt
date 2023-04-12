package com.project.space.services.invitation.response

import com.project.space.services.project.response.ProjectResponse
import com.project.space.services.user.response.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvitationResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("user")
    val user: UserResponse,
    @SerialName("project")
    val project: ProjectResponse
)
