package com.project.space.services.invitation

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.common.response.SimpleSuccessResponse
import com.project.space.services.invitation.builder.GetSessionUserInvitations
import com.project.space.services.invitation.builder.Invite
import com.project.space.services.invitation.response.InvitationsDataListResponse

class InvitationService(private val client: ProjectSpaceHttpClient) {
    suspend fun invite(email: String, projectId: Int): ProjectSpaceResult<SimpleSuccessResponse> =
        client.request(Invite(email = email, projectId = projectId))

    suspend fun getSessionUserInvitations(): ProjectSpaceResult<InvitationsDataListResponse> =
        client.request(GetSessionUserInvitations())
}
