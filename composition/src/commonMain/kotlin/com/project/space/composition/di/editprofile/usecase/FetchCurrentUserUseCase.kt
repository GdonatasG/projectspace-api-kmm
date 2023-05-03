package com.project.space.composition.di.editprofile.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.editprofile.domain.CurrentUser
import com.project.space.feature.editprofile.domain.EditProfile
import com.project.space.feature.editprofile.domain.FetchCurrentUser
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.user.UserService
import com.project.space.services.user.response.UserResponse

class FetchCurrentUserUseCase(
    private val scope: PlatformScopeManager,
    private val userService: UserService
) : FetchCurrentUser {
    override fun invoke(completion: (FetchCurrentUser.Response) -> Unit) {
        scope.launch {
            return@launch when (val response = userService.getSessionUser()) {
                is ProjectSpaceResult.Success -> {
                    completion(FetchCurrentUser.Response.Success(response.data.data.toDomain()))
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        FetchCurrentUser.Response.Error(
                            title = "Unable to get current profile",
                            message = response.error.errors[0].message
                        )
                    )
                }
                else -> {
                    completion(
                        FetchCurrentUser.Response.Error(
                            title = "Unable to get current profile",
                            message = "Something went wrong. Try again!"
                        )
                    )
                }
            }
        }
    }
}

private fun UserResponse.toDomain(): CurrentUser = CurrentUser(
    username = this.username,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    organizationName = this.organizationName ?: ""
)
