package com.libraries.http.ktor

import com.libraries.http.HttpClient
import com.libraries.http.exceptions.ConnectTimeoutException
import com.libraries.http.exceptions.UnresolvedAddressException
import com.libraries.http.exceptions.UrlEncodingException
import com.libraries.http.models.HttpRequest
import com.libraries.http.models.HttpResponse
import com.libraries.http.models.toKtor
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.coroutines.cancellation.CancellationException

internal class KtorHttpClient(
    private val client: io.ktor.client.HttpClient,
    private val baseUrl: String,
) : HttpClient {

    @Throws(
        CancellationException::class,
        UrlEncodingException::class,
        UnresolvedAddressException::class,
        ConnectTimeoutException::class
    )
    override suspend fun request(httpRequest: HttpRequest): HttpResponse =
        run {
            try {
                val response = client.request {
                    accept(ContentType.Application.Json)
                    httpRequest.headers.forEach { headerParam ->
                        headers.append(headerParam.key, headerParam.value)
                    }
                    method = httpRequest.method.toKtor()
                    setUrl(httpRequest)
                    httpRequest.body?.let {
                        setBody(it)
                    }
                }

                HttpResponse(
                    isSuccessful = response.status.isSuccess(),
                    code = response.status.value,
                    headers = response.headers.toMap(),
                    body = response.bodyAsText()
                )
            } catch (exception: io.ktor.util.network.UnresolvedAddressException) {
                throw UnresolvedAddressException(exception.message)
            } catch (exception: io.ktor.client.network.sockets.ConnectTimeoutException) {
                throw ConnectTimeoutException(exception.message)
            } catch (exception: HttpRequestTimeoutException) {
                throw ConnectTimeoutException(exception.message)
            }
        }

    @Suppress("SwallowedException")
    private fun HttpRequestBuilder.setUrl(httpRequest: HttpRequest) {
        try {
            this.url.takeFrom(URLBuilder(baseUrl + httpRequest.endpoint).apply {
                httpRequest.params?.parameters?.map { param ->
                    this.parameters.append(param.key, param.value)
                }
            }.build())
        } catch (exception: URLParserException) {
            throw UrlEncodingException(exception.message ?: "")
        }
    }

    private fun Headers.toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()

        this.forEach { s, strings ->
            map[s] = strings.joinToString(",")
        }

        return map
    }
}
