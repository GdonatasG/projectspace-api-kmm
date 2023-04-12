package com.project.space.services.utils

import com.libraries.http.HttpClient
import com.libraries.http.addLoggingInterceptor
import com.libraries.http.models.Token
import com.libraries.http.models.TokenType
import com.libraries.logger.PrintLogger
import com.project.space.services.common.Config
import com.project.space.services.common.http.DefaultProjectSpaceHttpClient
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.interceptor.addAuthorizationHandler

private fun createHttpClient(): HttpClient = HttpClient.create(ignoreTLS = true, baseUrl = Config.BASE_URL)
    .addLoggingInterceptor(logger = PrintLogger("TestHttpClient"))

internal fun createProjectSpaceHttpClient(): ProjectSpaceHttpClient = DefaultProjectSpaceHttpClient(
    client = createHttpClient()
)

internal fun createAuthorizedProjectSpaceHttpClient(): ProjectSpaceHttpClient = DefaultProjectSpaceHttpClient(
    client = createHttpClient()
).addAuthorizationHandler {
    Token(TokenType.BEARER, accessToken)
}

private const val accessToken: String =
"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzIiwicm9sZSI6IlVTRVIiLCJleHAiOjE2ODIxNzM0NTZ9.PyuN2QAcwztNBhI-6u4pwIB80K2UO5N_8cxTP-NO6vAPSgR_uui2Bvw5C8bJJefG7jeWVyWZ65RnPnbHZaQB0g"
