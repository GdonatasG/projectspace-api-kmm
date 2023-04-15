package com.project.space.services.task.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaskDataResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: TaskResponse
)
