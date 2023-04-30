package com.project.space.services.common

object Config {
    private const val DEFAULT_DNS = "192.168.1.104:8080"
    val BASE_URL = "http://$DEFAULT_DNS"
    const val AUTH_ENDPOINT = "/login"
    const val API_ENDPOINT = "/api"
}
