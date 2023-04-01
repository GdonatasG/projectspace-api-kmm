package com.project.space.services.authentication

import com.project.space.services.authentication.builder.Authenticate
import com.project.space.services.authentication.response.SuccessfulAuthenticationResponse
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request

class AuthenticationService(private val client: ProjectSpaceHttpClient) {
    suspend fun login(username: String, password: String): ProjectSpaceResult<SuccessfulAuthenticationResponse> =
        client.request(Authenticate(username = username, password = password))
}
