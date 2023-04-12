package com.project.space.services.invitation.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues
import com.project.space.services.project.value.ProjectValue
import com.project.space.services.user.value.UserValue

internal class Invite(email: String, projectId: Int) : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    init {
        bodyValues.put(UserValue.Email(email), ProjectValue.Id(projectId))
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.POST,
        endpoint = Config.API_ENDPOINT + "/project/invitations",
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
            "Content-Type" to "application/json"
        )
    )

}
