package com.project.space.services.common.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectSpaceErrorResponse(
    @SerialName("success") val success: Boolean, @SerialName("errors") val errors: List<ProjectSpaceErrorEntity>
)

@Serializable
data class ProjectSpaceErrorEntity(
    @SerialName("type") val type: String, @SerialName("message") val message: String
)
