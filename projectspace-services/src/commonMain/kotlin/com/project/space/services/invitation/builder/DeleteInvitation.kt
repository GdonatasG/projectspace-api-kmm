package com.project.space.services.invitation.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.libraries.http.models.QueryParams
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest

internal class DeleteInvitation(id: Int) : ProjectSpaceRequest {
    private val params: QueryParams = QueryParams()

    init {
        params.put("id", id)
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.DELETE,
        endpoint = Config.API_ENDPOINT + "/project/invitations",
        params = params
    )
}
