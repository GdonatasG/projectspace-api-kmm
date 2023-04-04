package com.project.space.services.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: UserResponse
)
