package com.project.space.composition.di.profile

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.di.RootContainer
import com.project.space.composition.di.profile.usecase.GetCurrentUserUseCase
import com.project.space.composition.di.profile.usecase.GetInvitationsCountUseCase
import com.project.space.composition.navigation.EditProfileFlow
import com.project.space.composition.navigation.Navigator
import com.project.space.composition.navigation.UserInvitationsFlow
import com.project.space.feature.common.AuthorizationStoreManager
import com.project.space.feature.profile.DefaultProfilePresenter
import com.project.space.feature.profile.ProfileDelegate
import com.project.space.feature.profile.ProfilePresenter
import com.project.space.feature.profile.domain.GetCurrentUser
import com.project.space.feature.profile.domain.GetInvitationsCount
import com.project.space.services.invitation.InvitationService

class ProfileContainer(
    private val container: RootContainer,
    private val navigator: Navigator,
    private val authorizationStoreManager: AuthorizationStoreManager,
    private val invitationService: InvitationService
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val getCurrentUserUseCase: GetCurrentUser by lazy {
        GetCurrentUserUseCase(
            authorizationStoreManager = authorizationStoreManager
        )
    }

    private val getInvitationsCountUseCase: GetInvitationsCount by lazy {
        GetInvitationsCountUseCase(
            scope = scope,
            invitationService = invitationService
        )
    }

    fun presenter(alert: Alert.Coordinator): ProfilePresenter = DefaultProfilePresenter(
        scope = scope,
        alert = alert,
        getCurrentUser = getCurrentUserUseCase,
        getInvitationsCount = getInvitationsCountUseCase,
        delegate = DefaultProfileDelegate(
            container = container,
            navigator = navigator,
            alert = alert
        ),
    )


}

private class DefaultProfileDelegate(
    private val container: RootContainer,
    private val navigator: Navigator,
    private val alert: Alert.Coordinator
) : ProfileDelegate {
    override fun onNavigateToEditProfile() {
        EditProfileFlow(
            container = container,
            navigator = navigator,
            alert = alert
        ).start()
    }

    override fun onNavigateToInvitations() {
        UserInvitationsFlow(
            container = container,
            navigator = navigator,
            alert = alert
        ).start()
    }

    override fun onLogout() {
        container.onLogout()
    }

}
