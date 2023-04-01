package com.project.space.services.authentication

import com.libraries.test.runTest
import com.project.space.services.utils.createProjectSpaceHttpClient
import kotlin.test.Test

class AuthenticationServiceSmokeTest {
    @Test
    fun `test login`() = runTest {
        val service: AuthenticationService = makeSUT()

        val response = service.login(username = "user123", password = "pass123")

        println(response.toString())
    }


    // region HELPERS
    private fun makeSUT(): AuthenticationService = AuthenticationService(client = createProjectSpaceHttpClient())
    // endregion
}
