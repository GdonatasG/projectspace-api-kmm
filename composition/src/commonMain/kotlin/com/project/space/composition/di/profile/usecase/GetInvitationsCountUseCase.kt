package com.project.space.composition.di.profile.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.profile.domain.GetInvitationsCount
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.invitation.InvitationService

class GetInvitationsCountUseCase(
    private val scope: PlatformScopeManager,
    private val invitationService: InvitationService
) : GetInvitationsCount {
    override fun invoke(completion: (GetInvitationsCount.Response) -> Unit) {
        scope.launch {
            return@launch when (val response = invitationService.getSessionUserInvitations()) {
                is ProjectSpaceResult.Success -> {
                    completion(GetInvitationsCount.Response.Success(count = response.data.data.size))
                }
                is ProjectSpaceResult.Error -> {
                    completion(GetInvitationsCount.Response.Error(message = response.error.errors[0].message))
                }
                else -> {
                    completion(GetInvitationsCount.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }
    }
}
