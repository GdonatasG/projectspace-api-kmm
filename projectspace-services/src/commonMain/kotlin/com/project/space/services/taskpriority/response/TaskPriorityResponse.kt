package com.project.space.services.taskpriority.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaskPriorityResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: TaskPriorityName
)

@kotlinx.serialization.Serializable
enum class TaskPriorityName {
    @SerialName("HIGH")
    HIGH,

    @SerialName("NORMAL")
    NORMAL,

    @SerialName("URGENT")
    URGENT
}
