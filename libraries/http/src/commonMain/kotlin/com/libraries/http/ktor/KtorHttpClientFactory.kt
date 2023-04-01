package com.libraries.http.ktor

import io.ktor.client.HttpClient

internal expect fun createKtorHttpClient(ignoreTLS: Boolean = false): HttpClient
