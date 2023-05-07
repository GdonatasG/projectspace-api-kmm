package com.project.space.feature.inviteuser

import com.libraries.utils.Presenter

abstract class InviteUserPresenter : Presenter<InviteUserView>() {
    abstract fun onInviteUser(email: String)
    abstract fun onNavigateBack()
}
