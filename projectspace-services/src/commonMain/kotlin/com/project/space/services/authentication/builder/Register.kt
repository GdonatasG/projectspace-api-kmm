package com.project.space.services.authentication.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues
import com.project.space.services.user.value.UserValue

class Register(
    username: String,
    firstName: String,
    lastName: String,
    email: String,
    password: String
) : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    init {
        bodyValues.put(
            UserValue.Username(username),
            UserValue.FirstName(firstName),
            UserValue.LastName(lastName),
            UserValue.Email(email),
            UserValue.Password(password),
            UserValue.Role("USER")
        )
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.POST,
        endpoint = Config.API_ENDPOINT + "/user",
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
            "Content-Type" to "application/json"
        )
    )

    fun organizationName(value: String): Register {
        bodyValues.put(UserValue.OrganizationName(value))

        return this
    }
}
