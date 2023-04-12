package com.project.space.services.taskstatus.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaskStatusResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: TaskName
)

@kotlinx.serialization.Serializable
enum class TaskName {
    @SerialName("CLOSED")
    CLOSED,
    @SerialName("OPEN")
    OPEN
}
