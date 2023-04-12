package com.project.space.services.projectmemberlevel.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ProjectMemberLevelsDataListResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: List<ProjectMemberLevelResponse>
)
