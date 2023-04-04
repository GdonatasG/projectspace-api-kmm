package com.project.space.services.auth

import com.project.space.services.auth.builder.Login
import com.project.space.services.auth.builder.Register
import com.project.space.services.auth.response.SuccessfulAuthenticationResponse
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.common.response.SimpleSuccessResponse

class AuthService(private val client: ProjectSpaceHttpClient) {
    suspend fun login(username: String, password: String): ProjectSpaceResult<SuccessfulAuthenticationResponse> =
        client.request(Login(username = username, password = password))

    suspend fun register(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        builder: Register.() -> Unit = {}
    ): ProjectSpaceResult<SimpleSuccessResponse> = client.request(
        Register(
            username = username, firstName = firstName, lastName = lastName, email = email, password = password
        ).apply(
            builder
        )
    )
}
