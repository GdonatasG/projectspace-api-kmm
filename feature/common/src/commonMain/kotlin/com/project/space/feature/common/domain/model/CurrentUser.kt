package com.project.space.feature.common.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUser(
    val id: Int, val username: String, val email: String, val role: UserRole
)

@Serializable
enum class UserRole {
    @SerialName("USER")
    USER,

    @SerialName("ADMIN")
    ADMIN
}
