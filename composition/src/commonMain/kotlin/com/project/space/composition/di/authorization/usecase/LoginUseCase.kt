package com.project.space.composition.di.authorization.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.utils.mapper.toDomain
import com.project.space.feature.authorization.domain.Login
import com.project.space.feature.common.domain.AuthorizationStoreManager
import com.project.space.feature.common.domain.model.AuthorizationState
import com.project.space.services.auth.AuthService
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.user.UserService

class LoginUseCase(
    private val scope: PlatformScopeManager,
    private val authService: AuthService,
    private val userService: UserService,
    private val authorizationStoreManager: AuthorizationStoreManager
) : Login {
    override fun invoke(username: String, password: String, completion: (Login.Response) -> Unit) {
        scope.launch {
            return@launch when (val loginResponse = authService.login(username = username, password = password)) {
                is ProjectSpaceResult.Success -> {
                    authorizationStoreManager.setAuthState(AuthorizationState(accessToken = loginResponse.data.token))

                    return@launch when (val userResponse = userService.getSessionUser()) {
                        is ProjectSpaceResult.Success -> {
                            authorizationStoreManager.setCurrentUser(userResponse.data.data.toDomain())
                            completion(Login.Response.Success)
                        }
                        else -> {
                            completion(Login.Response.Error(message = "Something went wrong. Try again!"))
                        }
                    }
                }
                is ProjectSpaceResult.Error -> {
                    completion(Login.Response.Error(message = loginResponse.error.errors[0].message))
                }
                else -> {
                    completion(Login.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }
    }
}
