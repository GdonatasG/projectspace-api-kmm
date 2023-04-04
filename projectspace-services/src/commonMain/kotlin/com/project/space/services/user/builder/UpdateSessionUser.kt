package com.project.space.services.user.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues
import com.project.space.services.user.value.UserValue

class UpdateSessionUser : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.PUT,
        endpoint = Config.API_ENDPOINT + "/user",
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
            "Content-Type" to "application/json"
        )
    )

    fun firstName(value: String): UpdateSessionUser {
        bodyValues.put(UserValue.FirstName(value))

        return this
    }

    fun lastName(value: String): UpdateSessionUser {
        bodyValues.put(UserValue.LastName(value))

        return this
    }

    fun organizationName(value: String): UpdateSessionUser {
        bodyValues.put(UserValue.OrganizationName(value))

        return this
    }
}
