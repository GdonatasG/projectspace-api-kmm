package com.project.space.services.project.response

import com.project.space.services.user.response.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDataResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: ProjectResponse
)

@Serializable
data class ProjectResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("owner")
    val owner: UserResponse,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String? = null
)
