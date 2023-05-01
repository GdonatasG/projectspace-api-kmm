package com.project.space.composition.di.userinvitations.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.userinvitations.domain.GetInvitations
import com.project.space.feature.userinvitations.domain.Invitation
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.invitation.InvitationService
import com.project.space.services.invitation.response.InvitationResponse

class GetUserInvitationsUseCase(
    private val scope: PlatformScopeManager,
    private val invitationService: InvitationService
) : GetInvitations {
    override fun invoke(completion: (GetInvitations.Response) -> Unit) {
        scope.launch {
            return@launch when (val response = invitationService.getSessionUserInvitations()) {
                is ProjectSpaceResult.Success -> {
                    completion(GetInvitations.Response.Success(data = response.data.data.map { it.toDomain() }))
                }
                is ProjectSpaceResult.Error -> {
                    completion(GetInvitations.Response.Error(message = response.error.errors[0].message))
                }
                else -> {
                    completion(GetInvitations.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }
    }
}

private fun InvitationResponse.toDomain() = Invitation(
    id = this.id,
    projectName = this.project.name,
    ownerName = this.project.owner.username
)
