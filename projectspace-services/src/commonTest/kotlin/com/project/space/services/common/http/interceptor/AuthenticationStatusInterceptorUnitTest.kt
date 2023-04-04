package com.project.space.services.common.http.interceptor

import com.libraries.test.runTest
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.common.http.utils.SpyProjectSpaceHttpClient
import com.project.space.services.common.http.utils.TestProjectSpaceRequest
import com.project.space.services.common.http.utils.TestProjectSpaceResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthenticationStatusInterceptorUnitTest {
    @Test
    fun `test request does not retry HTTP call when Unauthorized and invokes onUnauthorized callback`() = runTest {
        val spyHttpClient = SpyProjectSpaceHttpClient(ProjectSpaceResult.Unauthorized)
        var invokedOnUnauthorized = false

        val sut: ProjectSpaceHttpClient = makeSUT(spyHttpClient, onUnauthorized = {
            invokedOnUnauthorized = true
        })

        sut.request<TestProjectSpaceResponse>(TestProjectSpaceRequest())

        assertEquals(1, spyHttpClient.requests.size)
        assertEquals(1, spyHttpClient.responses.size)
        assertEquals(true, invokedOnUnauthorized)
    }

    @Test
    fun `test request does not invoke onUnauthorized callback when Authorized`() = runTest {
        val spyHttpClient =
            SpyProjectSpaceHttpClient(ProjectSpaceResult.Success(TestProjectSpaceResponse(success = true)))
        var invokedOnUnauthorized = false

        val sut: ProjectSpaceHttpClient = makeSUT(spyHttpClient, onUnauthorized = {
            invokedOnUnauthorized = true
        })

        sut.request<TestProjectSpaceResponse>(TestProjectSpaceRequest())

        assertEquals(1, spyHttpClient.requests.size)
        assertEquals(1, spyHttpClient.responses.size)
        assertEquals(false, invokedOnUnauthorized)
    }


    // region HELPERS
    private fun makeSUT(spyHttpClient: SpyProjectSpaceHttpClient, onUnauthorized: () -> Unit): ProjectSpaceHttpClient {
        return AuthenticationStatusInterceptor(client = spyHttpClient, onUnauthorized = onUnauthorized)
    }
    // endregion
}
