package com.project.space.services.projectmember.response

import com.project.space.services.project.response.ProjectResponse
import com.project.space.services.projectmemberlevel.response.ProjectMemberLevelResponse
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ProjectMemberResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("project")
    val project: ProjectResponse,
    @SerialName("level")
    val level: ProjectMemberLevelResponse
)
