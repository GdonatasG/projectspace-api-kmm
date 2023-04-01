package com.libraries.http.interceptors

import com.libraries.http.HttpClient
import com.libraries.http.models.HttpRequest
import com.libraries.http.models.HttpResponse

class Interceptor(
    private val client: HttpClient,
    private val body: suspend () -> Unit
) : HttpClient {

    override suspend fun request(httpRequest: HttpRequest): HttpResponse {
        return intercept {
            client.request(httpRequest)
        }
    }


    private suspend fun intercept(
        request: suspend () -> HttpResponse
    ): HttpResponse {
        body()

        return request()
    }
}
