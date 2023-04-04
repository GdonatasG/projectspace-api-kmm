package com.project.space.services.common.http.interceptor

import com.libraries.http.models.Token
import com.libraries.http.models.TokenType
import com.libraries.test.runTest
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.common.http.utils.SpyProjectSpaceHttpClient
import com.project.space.services.common.http.utils.TestProjectSpaceRequest
import com.project.space.services.common.http.utils.TestProjectSpaceResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthorizationInterceptorUnitTest {
    @Test
    fun `test request adds authorization token`() = runTest {
        val spyHttpClient = SpyProjectSpaceHttpClient()

        val sut: ProjectSpaceHttpClient = makeSUT(spyHttpClient = spyHttpClient)

        sut.request<TestProjectSpaceResponse>(TestProjectSpaceRequest())

        assertEquals(1, spyHttpClient.requests.size)
        assertEquals(testToken.get(), spyHttpClient.requests.first().build().headers["Authorization"])
    }

    // region HELPERS
    private fun makeSUT(spyHttpClient: SpyProjectSpaceHttpClient): ProjectSpaceHttpClient {
        return AuthorizationInterceptor(spyHttpClient) {
            return@AuthorizationInterceptor testToken
        }
    }

    private val testToken = Token(
        TokenType.BEARER,
        "testToken"
    )
    // endregion
}
