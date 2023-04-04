package com.project.space.services.project.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectRiskResponse(
    @SerialName("total_tasks")
    val totalTasks: Int,
    @SerialName("open_tasks")
    val openTasks: Int,
    @SerialName("risk")
    val risk: ProjectRisk
)

@Serializable
enum class ProjectRisk {
    @SerialName("UNKNOWN")
    UNKNOWN,

    @SerialName("NO_RISK")
    NO_RISK,

    @SerialName("LOW")
    LOW,

    @SerialName("MEDIUM")
    MEDIUM,

    @SerialName("HIGH")
    HIGH
}
