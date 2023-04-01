package com.libraries.http.models

data class HttpResponse(
    val isSuccessful: Boolean,
    val code: Int,
    val headers: Map<String, String>,
    val body: String,
)
