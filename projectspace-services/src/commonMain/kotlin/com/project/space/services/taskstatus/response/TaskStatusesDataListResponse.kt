package com.project.space.services.taskstatus.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaskStatusesDataListResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: List<TaskStatusResponse>
)
