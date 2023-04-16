package com.project.space.composition.di.authorization.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.authorization.domain.Register
import com.project.space.services.auth.AuthService
import com.project.space.services.common.ProjectSpaceResult

class RegisterUseCase(
    private val scope: PlatformScopeManager,
    private val authService: AuthService,
) : Register {
    override fun invoke(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        completion: (Register.Response) -> Unit
    ) {
        scope.launch {
            return@launch when (val registerResponse = authService.register(
                username = username, firstName = firstName, lastName = lastName, email = email, password = password
            )) {
                is ProjectSpaceResult.Success -> {
                    completion(Register.Response.Success)
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        Register.Response.InputErrors(
                            data = registerResponse.error.errors.map {
                                Register.InputError(
                                    input = it.type,
                                    message = it.message
                                )
                            }
                        )
                    )
                }
                else -> {
                    completion(Register.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }

    }
}
