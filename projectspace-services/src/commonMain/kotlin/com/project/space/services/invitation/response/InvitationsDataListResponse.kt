package com.project.space.services.invitation.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvitationsDataListResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: List<InvitationResponse>
)
