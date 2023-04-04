package com.project.space.services.common.http.interceptor

import com.libraries.http.models.HttpRequest
import com.libraries.http.models.Token
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.request.ProjectSpaceRequest
import kotlin.reflect.KClass

class AuthorizationInterceptor(
    private val client: ProjectSpaceHttpClient, private val tokenProvider: suspend () -> Token
) : ProjectSpaceHttpClient {
    override suspend fun <T : Any> request(request: ProjectSpaceRequest, clazz: KClass<T>): ProjectSpaceResult<T> {
        val token = tokenProvider()

        return client.request(AuthorizationInterceptorWrapper(request = request, token = token), clazz)
    }

    private class AuthorizationInterceptorWrapper(
        private val request: ProjectSpaceRequest, private val token: Token
    ) : ProjectSpaceRequest {
        override fun build(): HttpRequest {
            val httpRequest = request.build()

            return HttpRequest(
                method = httpRequest.method,
                endpoint = httpRequest.endpoint,
                body = httpRequest.body,
                params = httpRequest.params,
                headers = mapOf("Authorization" to token.get()) + httpRequest.headers
            )
        }

    }
}

fun ProjectSpaceHttpClient.addAuthorizationHandler(tokenProvider: suspend () -> Token): ProjectSpaceHttpClient =
    AuthorizationInterceptor(client = this, tokenProvider = tokenProvider)
