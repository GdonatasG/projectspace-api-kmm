@file:Suppress("NAME_SHADOWING", "EXTENSION_SHADOWED_BY_MEMBER")

package com.libraries.http

import com.libraries.http.exceptions.ConnectTimeoutException
import com.libraries.http.exceptions.UnresolvedAddressException
import com.libraries.http.exceptions.UrlEncodingException
import com.libraries.http.interceptors.HttpLoggingInterceptor
import com.libraries.http.interceptors.Interceptor
import com.libraries.http.ktor.KtorHttpClient
import com.libraries.http.ktor.createKtorHttpClient
import com.libraries.http.models.HttpRequest
import com.libraries.http.models.HttpResponse
import com.libraries.logger.Logger
import kotlin.coroutines.cancellation.CancellationException

interface HttpClient {
    companion object {
        fun create(
            ignoreTLS: Boolean = false,
            baseUrl: String,
        ): HttpClient = KtorHttpClient(
            client = createKtorHttpClient(ignoreTLS), baseUrl = baseUrl
        )
    }

    @Throws(
        CancellationException::class,
        UrlEncodingException::class,
        UnresolvedAddressException::class,
        ConnectTimeoutException::class
    )
    suspend fun request(httpRequest: HttpRequest): HttpResponse
}

fun HttpClient.addLoggingInterceptor(
    level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC, logger: Logger
): HttpClient = HttpLoggingInterceptor(this, level, logger)

fun HttpClient.intercept(
    body: suspend (client: HttpClient) -> Unit
): HttpClient = Interceptor(this) { body(this) }
