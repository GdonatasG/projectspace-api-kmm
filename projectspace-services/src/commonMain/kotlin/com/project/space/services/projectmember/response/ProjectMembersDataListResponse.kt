package com.project.space.services.projectmember.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ProjectMembersDataListResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: List<ProjectMemberResponse>
)
