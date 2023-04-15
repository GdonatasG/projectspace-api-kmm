package com.project.space.services.task.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TasksDataListResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: List<TaskResponse>
)
