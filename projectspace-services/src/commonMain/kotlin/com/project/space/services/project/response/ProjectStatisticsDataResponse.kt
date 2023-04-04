package com.project.space.services.project.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectStatisticsDataResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: ProjectStatisticsResponse
)
