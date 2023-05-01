package com.project.space.composition.di.userinvitations.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.userinvitations.domain.AcceptInvitation
import com.project.space.feature.userinvitations.domain.Invitation
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.invitation.InvitationService

class AcceptInvitationUseCase(
    private val scope: PlatformScopeManager,
    private val invitationService: InvitationService
) : AcceptInvitation {
    override fun invoke(invitation: Invitation, completion: (AcceptInvitation.Response) -> Unit) {
        scope.launch {
            return@launch when (val response = invitationService.acceptInvitation(invitation.id)) {
                is ProjectSpaceResult.Success -> {
                    completion(AcceptInvitation.Response.Success)
                }
                is ProjectSpaceResult.Error -> {
                    completion(AcceptInvitation.Response.Error(message = response.error.errors[0].message))
                }
                else -> {
                    completion(AcceptInvitation.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }
    }
}
