package com.project.space.services.common.http.interceptor

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.request.ProjectSpaceRequest
import kotlin.reflect.KClass

class AuthenticationStatusInterceptor(
    private val client: ProjectSpaceHttpClient,
    private val onUnauthorized: () -> Unit
) : ProjectSpaceHttpClient {
    override suspend fun <T : Any> request(request: ProjectSpaceRequest, clazz: KClass<T>): ProjectSpaceResult<T> {
        return checkAuthorizationStatus {
            client.request(request, clazz)
        }
    }

    private suspend fun <T : Any> checkAuthorizationStatus(
        request: suspend () -> ProjectSpaceResult<T>
    ): ProjectSpaceResult<T> {
        val response: ProjectSpaceResult<T> = request()

        return if (response is ProjectSpaceResult.Unauthorized) {
            onUnauthorized()

            response
        } else {
            response
        }
    }
}

fun ProjectSpaceHttpClient.addAuthenticationStatusHandler(onUnauthorized: () -> Unit): ProjectSpaceHttpClient =
    AuthenticationStatusInterceptor(this) { onUnauthorized() }
