package com.libraries.http.models

import io.ktor.http.HttpMethod as KtorHttpMethod

enum class HttpMethod {
    GET, PUT, POST, DELETE
}

fun HttpMethod.toKtor(): KtorHttpMethod = when (this) {
    HttpMethod.GET -> KtorHttpMethod.Get
    HttpMethod.PUT -> KtorHttpMethod.Put
    HttpMethod.POST -> KtorHttpMethod.Post
    HttpMethod.DELETE -> KtorHttpMethod.Delete
}
