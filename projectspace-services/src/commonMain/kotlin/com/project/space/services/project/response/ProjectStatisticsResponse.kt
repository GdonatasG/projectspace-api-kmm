package com.project.space.services.project.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectStatisticsResponse(
    @SerialName("total_tasks")
    val totalTasks: Int,
    @SerialName("open_tasks")
    val openTasks: Int,
    @SerialName("closed_tasks")
    val closedTasks: Int
)
