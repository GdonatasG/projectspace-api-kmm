package com.project.space.services.authentication.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SuccessfulAuthenticationResponse(
    @SerialName("token")
    val token: String
)
