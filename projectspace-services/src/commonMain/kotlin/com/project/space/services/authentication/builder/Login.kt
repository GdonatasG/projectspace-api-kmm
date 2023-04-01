package com.project.space.services.authentication.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.user.value.UserValue
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues

internal class Login(username: String, password: String) : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    init {
        bodyValues.put(UserValue.Username(username))
        bodyValues.put(UserValue.Password(password))
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.POST,
        endpoint = Config.AUTH_ENDPOINT,
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
            "Content-Type" to "application/json"
        )
    )
}
