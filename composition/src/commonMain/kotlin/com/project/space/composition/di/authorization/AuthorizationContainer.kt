package com.project.space.composition.di.authorization

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.di.authorization.usecase.LoginUseCase
import com.project.space.composition.di.authorization.usecase.RegisterUseCase
import com.project.space.feature.authorization.AuthorizationPresenter
import com.project.space.feature.authorization.DefaultAuthorizationPresenter
import com.project.space.feature.authorization.domain.Login
import com.project.space.feature.authorization.domain.Register
import com.project.space.feature.common.AuthorizationStoreManager
import com.project.space.services.auth.AuthService
import com.project.space.services.user.UserService

class AuthorizationContainer(
    private val authorizationStoreManager: AuthorizationStoreManager,
    private val authService: AuthService,
    private val userService: UserService,
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val loginUseCase: Login by lazy {
        LoginUseCase(
            scope = scope,
            authService = authService,
            userService = userService,
            authorizationStoreManager = authorizationStoreManager
        )
    }

    private val registerUseCase: Register by lazy {
        RegisterUseCase(
            scope = scope,
            authService = authService
        )
    }

    fun presenter(alert: Alert.Coordinator, onAuthorized: () -> Unit): AuthorizationPresenter =
        DefaultAuthorizationPresenter(
            scope = scope,
            alert = alert,
            login = loginUseCase,
            register = registerUseCase,
            onAuthorized = onAuthorized
        )

}
