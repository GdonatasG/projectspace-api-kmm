package com.project.space.composition.di

import com.libraries.http.HttpClient
import com.libraries.http.addLoggingInterceptor
import com.libraries.http.interceptors.HttpLoggingInterceptor
import com.libraries.http.models.Token
import com.libraries.http.models.TokenType
import com.libraries.logger.NapierLogger
import com.libraries.preferences.Preferences
import com.libraries.utils.isDebug
import com.project.space.composition.di.authorization.AuthorizationContainer
import com.project.space.composition.navigation.Navigator
import com.project.space.feature.common.domain.model.AuthorizationState
import com.project.space.feature.common.domain.AuthorizationStoreManager
import com.project.space.feature.common.domain.DefaultAuthorizationStoreManager
import com.project.space.services.auth.AuthService
import com.project.space.services.common.Config
import com.project.space.services.common.http.DefaultProjectSpaceHttpClient
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.interceptor.addAuthenticationStatusHandler
import com.project.space.services.common.http.interceptor.addAuthorizationHandler
import com.project.space.services.common.http.interceptor.baseUrl
import com.project.space.services.user.UserService
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class RootContainer(
    private val navigator: Navigator
) {
    init {
        if (isDebug) {
            Napier.base(DebugAntilog())
        }
    }

    private val authorizationStoreManager: AuthorizationStoreManager by lazy {
        DefaultAuthorizationStoreManager(Preferences.shared)
    }

    val session: AuthorizationState?
        get() = authorizationStoreManager.getAuthState()

    private val httpClient: HttpClient by lazy {
        HttpClient.create(ignoreTLS = true)
            .addLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC, NapierLogger(tag = "HttpClient"))
    }

    private val authorizationHttpClient: ProjectSpaceHttpClient by lazy {
        DefaultProjectSpaceHttpClient(client = httpClient)
            .baseUrl(Config.BASE_URL)
    }

    private val authService: AuthService by lazy {
        AuthService(
            client = authorizationHttpClient
        )
    }

    private val spaceHttpClient: ProjectSpaceHttpClient by lazy {
        DefaultProjectSpaceHttpClient(client = httpClient)
            .baseUrl(Config.BASE_URL)
            .addAuthorizationHandler {
                Token(TokenType.BEARER, authorizationStoreManager.getAuthState()?.accessToken ?: "")
            }
            .addAuthenticationStatusHandler {
                authorizationStoreManager.clearAuthState()
                authorizationStoreManager.clearCurrentUser()
                authorizationStoreManager.clearSelectedProject()

                // TODO: logout
            }
    }

    private val userService: UserService by lazy {
        UserService(
            client = spaceHttpClient
        )
    }

    fun authorization(): AuthorizationContainer = AuthorizationContainer(
        authorizationStoreManager = authorizationStoreManager,
        authService = authService,
        userService = userService
    )
}
