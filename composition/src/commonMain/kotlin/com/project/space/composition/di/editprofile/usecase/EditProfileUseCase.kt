package com.project.space.composition.di.editprofile.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.editprofile.domain.EditProfile
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.user.UserService

class EditProfileUseCase(
    private val scope: PlatformScopeManager,
    private val userService: UserService
) : EditProfile {
    override fun invoke(
        firstName: String?,
        lastName: String?,
        organizationName: String?,
        completion: (EditProfile.Response) -> Unit
    ) {
        scope.launch {
            return@launch when (val response = userService.updateSessionUser {
                firstName?.let {
                    firstName(it)
                }
                lastName?.let {
                    lastName(it)
                }
                organizationName?.let {
                    organizationName(it)
                }
            }) {
                is ProjectSpaceResult.Success -> {
                    completion(EditProfile.Response.Success)
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        EditProfile.Response.InputErrors(
                            data = response.error.errors.map {
                                EditProfile.InputError(
                                    input = it.type,
                                    message = it.message
                                )
                            }
                        )
                    )
                }
                else -> {
                    completion(
                        EditProfile.Response.Error(
                            title = "Unable to update profile",
                            message = "Something went wrong. Try again!"
                        )
                    )
                }
            }
        }
    }
}
