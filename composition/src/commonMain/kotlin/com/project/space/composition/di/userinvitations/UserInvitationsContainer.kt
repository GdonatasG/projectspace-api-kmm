package com.project.space.composition.di.userinvitations

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.di.userinvitations.usecase.AcceptInvitationUseCase
import com.project.space.composition.di.userinvitations.usecase.GetUserInvitationsUseCase
import com.project.space.composition.navigation.Navigator
import com.project.space.feature.userinvitations.DefaultUserInvitationsPresenter
import com.project.space.feature.userinvitations.UserInvitationsPresenter
import com.project.space.feature.userinvitations.domain.AcceptInvitation
import com.project.space.feature.userinvitations.domain.GetInvitations
import com.project.space.feature.userinvitations.domain.UserInvitationsDelegate
import com.project.space.services.invitation.InvitationService

class UserInvitationsContainer(
    private val navigator: Navigator,
    private val invitationService: InvitationService
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val getUserInvitations: GetInvitations by lazy {
        GetUserInvitationsUseCase(
            scope = scope,
            invitationService = invitationService
        )
    }

    private val acceptInvitationUseCase: AcceptInvitation by lazy {
        AcceptInvitationUseCase(
            scope = scope,
            invitationService = invitationService
        )
    }


    fun presenter(alert: Alert.Coordinator): UserInvitationsPresenter = DefaultUserInvitationsPresenter(
        scope = scope,
        alert = alert,
        getInvitations = getUserInvitations,
        acceptInvitation = acceptInvitationUseCase,
        delegate = DefaultUserInvitationsDelegate(navigator = navigator)
    )
}

private class DefaultUserInvitationsDelegate(
    private val navigator: Navigator
) : UserInvitationsDelegate {
    override fun onNavigateBack() {
        navigator.pop()
    }
}
