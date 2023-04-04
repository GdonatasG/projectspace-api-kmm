package com.project.space.services.project.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectRiskDataResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: ProjectRiskResponse
)
