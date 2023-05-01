package com.project.space.feature.userinvitations

import com.libraries.utils.Presenter
import com.project.space.feature.userinvitations.domain.Invitation

abstract class UserInvitationsPresenter : Presenter<UserInvitationsView>() {
    abstract fun onNavigateBack()
    abstract fun onAccept(invitation: Invitation)
    abstract fun onRefresh()
    abstract fun onRetry()
}
