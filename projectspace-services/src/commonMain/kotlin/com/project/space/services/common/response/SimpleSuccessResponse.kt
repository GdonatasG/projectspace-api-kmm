package com.project.space.services.common.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleSuccessResponse(
    @SerialName("success")
    val success: Boolean
)
