package com.libraries.http.models

data class HttpRequest(
    val method: HttpMethod = HttpMethod.GET,
    val endpoint: String = "",
    val body: String? = null,
    val params: QueryParams? = null,
    val headers: Map<String, String> = emptyMap()
)

enum class HttpProtocol(val value: String) {
    HTTP("http://"),
    HTTPS("https://")
}
