package com.project.space.services.invitation.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest

internal class GetSessionUserInvitations: ProjectSpaceRequest {
    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.GET,
        endpoint = Config.API_ENDPOINT + "/project/invitations/currentUser"
    )
}
