package com.project.space.services.taskpriority.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaskPrioritiesDataListResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: List<TaskPriorityResponse>
)
