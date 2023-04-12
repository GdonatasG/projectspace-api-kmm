package com.project.space.services.projectmemberlevel.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ProjectMemberLevelResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: ProjectMemberLevelName
)

@kotlinx.serialization.Serializable
enum class ProjectMemberLevelName {
    @SerialName("MEMBER")
    MEMBER,

    @SerialName("MODERATOR")
    MODERATOR,

    @SerialName("OWNER")
    OWNER
}
