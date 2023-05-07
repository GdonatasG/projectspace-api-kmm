package com.project.space.composition.di.inviteuser

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.navigation.Navigator
import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.inviteuser.DefaultInviteUserPresenter
import com.project.space.feature.inviteuser.InviteUserDelegate
import com.project.space.feature.inviteuser.InviteUserPresenter
import com.project.space.feature.inviteuser.domain.InviteUser
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.invitation.InvitationService

class InviteUserContainer(
    private val invitationService: InvitationService,
    private val selectedProjectManager: SelectedProjectManager
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val inviteUserUseCase: InviteUser by lazy {
        InviteUserUseCase(
            scope = scope, invitationService = invitationService, selectedProjectManager = selectedProjectManager
        )
    }

    fun presenter(alert: Alert.Coordinator, delegate: InviteUserDelegate): InviteUserPresenter =
        DefaultInviteUserPresenter(
            scope = scope, delegate = delegate, alert = alert, inviteUser = inviteUserUseCase
        )
}

private class InviteUserUseCase(
    private val scope: PlatformScopeManager,
    private val invitationService: InvitationService,
    private val selectedProjectManager: SelectedProjectManager
) : InviteUser {
    override fun invoke(email: String, completion: (InviteUser.Response) -> Unit) {
        val projectId: Int = selectedProjectManager.getSelectedProject()?.id ?: return


        scope.launch {
            return@launch when (val response = invitationService.invite(email = email, projectId = projectId)) {
                is ProjectSpaceResult.Success -> {
                    completion(InviteUser.Response.Success)
                }
                is ProjectSpaceResult.Error -> {
                    completion(InviteUser.Response.InputErrors(data = response.error.errors.map {
                        InviteUser.InputError(
                            input = it.type, message = it.message
                        )
                    }))
                }
                else -> {
                    completion(
                        InviteUser.Response.Error(
                            title = "Unable to invite user", message = "Something went wrong. Try again!"
                        )
                    )
                }
            }
        }
    }

}
