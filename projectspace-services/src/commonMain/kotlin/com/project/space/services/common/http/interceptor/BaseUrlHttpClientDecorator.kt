package com.project.space.services.common.http.interceptor

import com.libraries.http.models.HttpRequest
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.request.ProjectSpaceRequest
import kotlin.reflect.KClass

class BaseUrlHttpClientDecorator(
    private val client: ProjectSpaceHttpClient, private val baseUrl: String
) : ProjectSpaceHttpClient {
    override suspend fun <T : Any> request(request: ProjectSpaceRequest, clazz: KClass<T>): ProjectSpaceResult<T> {
        return client.request(Request(request, baseUrl), clazz)
    }

    private class Request(
        private val request: ProjectSpaceRequest, private val baseUrl: String
    ) : ProjectSpaceRequest {
        val httpRequest = request.build()

        override fun build(): HttpRequest = HttpRequest(
            method = httpRequest.method,
            endpoint = baseUrl + httpRequest.endpoint,
            body = httpRequest.body,
            params = httpRequest.params,
            headers = httpRequest.headers
        )

    }
}

fun ProjectSpaceHttpClient.baseUrl(url: String): ProjectSpaceHttpClient = BaseUrlHttpClientDecorator(this, url)
