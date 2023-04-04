package com.project.space.services.common

object Config {
    private const val DEFAULT_DNS = "localhost:8080"
    val BASE_URL = "http://$DEFAULT_DNS"
    const val AUTH_ENDPOINT = "/login"
    const val API_ENDPOINT = "/api"
}
