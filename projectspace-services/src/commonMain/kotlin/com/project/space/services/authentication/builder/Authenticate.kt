package com.project.space.services.authentication.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValue
import com.project.space.services.common.value.BodyValues
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

internal class Authenticate(username: String, password: String) : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    init {
        bodyValues.put(Username(username))
        bodyValues.put(Password(password))
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

private data class Username(private val value: String) : BodyValue {
    override val key: String = "username"
    override val jsonElement: JsonElement = JsonPrimitive(value)
}

private data class Password(private val value: String) : BodyValue {
    override val key: String = "password"
    override val jsonElement: JsonElement = JsonPrimitive(value)
}
