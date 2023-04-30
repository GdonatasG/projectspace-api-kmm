package com.project.space.composition.di

import com.libraries.alerts.Alert
import com.libraries.http.HttpClient
import com.libraries.http.addLoggingInterceptor
import com.libraries.http.interceptors.HttpLoggingInterceptor
import com.libraries.http.models.Token
import com.libraries.http.models.TokenType
import com.libraries.logger.NapierLogger
import com.libraries.preferences.Preferences
import com.libraries.utils.isDebug
import com.project.space.composition.di.authorization.AuthorizationContainer
import com.project.space.composition.di.projects.ProjectsContainer
import com.project.space.composition.navigation.AuthorizationFlow
import com.project.space.composition.navigation.Navigator
import com.project.space.feature.common.domain.model.AuthorizationState
import com.project.space.feature.common.AuthorizationStoreManager
import com.project.space.feature.common.DefaultAuthorizationStoreManager
import com.project.space.feature.common.DefaultSelectedProjectManager
import com.project.space.feature.common.SelectedProjectManager
import com.project.space.services.auth.AuthService
import com.project.space.services.common.Config
import com.project.space.services.common.http.DefaultProjectSpaceHttpClient
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.interceptor.addAuthenticationStatusHandler
import com.project.space.services.common.http.interceptor.addAuthorizationHandler
import com.project.space.services.common.http.interceptor.baseUrl
import com.project.space.services.project.ProjectService
import com.project.space.services.user.UserService
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class RootContainer(
    private val navigator: Navigator,
    private val alert: Alert.Coordinator
) {
    init {
        if (isDebug) {
            Napier.base(DebugAntilog())
        }
    }

    private val authorizationStoreManager: AuthorizationStoreManager by lazy {
        DefaultAuthorizationStoreManager(Preferences.shared)
    }

    private val selectedProjectManager: SelectedProjectManager by lazy {
        DefaultSelectedProjectManager(Preferences.shared)
    }

    val session: AuthorizationState?
        get() = authorizationStoreManager.getAuthState()

    private val httpClient: HttpClient by lazy {
        HttpClient.create(ignoreTLS = true)
            .addLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC, NapierLogger(tag = "HttpClient"))
    }

    private val authorizationHttpClient: ProjectSpaceHttpClient by lazy {
        DefaultProjectSpaceHttpClient(client = httpClient).baseUrl(Config.BASE_URL)
    }

    private val authService: AuthService by lazy {
        AuthService(
            client = authorizationHttpClient
        )
    }

    private val spaceHttpClient: ProjectSpaceHttpClient by lazy {
        DefaultProjectSpaceHttpClient(client = httpClient).baseUrl(Config.BASE_URL).addAuthorizationHandler {
            Token(TokenType.BEARER, authorizationStoreManager.getAuthState()?.accessToken ?: "")
        }.addAuthenticationStatusHandler(onUnauthorized = {
            authorizationStoreManager.clearAuthState()
            authorizationStoreManager.clearCurrentUser()
            selectedProjectManager.clearSelectedProject()

            navigator.startAuthorization(authorization().presenter(alert = alert, onAuthorized = {
                navigator.startMain()
            }))
        })
    }

    private val userService: UserService by lazy {
        UserService(
            client = spaceHttpClient
        )
    }

    private val projectService: ProjectService by lazy {
        ProjectService(
            client = spaceHttpClient
        )
    }

    fun authorization(): AuthorizationContainer = AuthorizationContainer(
        authorizationStoreManager = authorizationStoreManager, authService = authService, userService = userService
    )

    fun projects(): ProjectsContainer = ProjectsContainer(
        projectService = projectService,
        selectedProjectManager = selectedProjectManager,
        selectedProjectObservable = selectedProjectManager
    )
}
