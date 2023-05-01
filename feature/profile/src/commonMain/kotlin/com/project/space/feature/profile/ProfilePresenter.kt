package com.project.space.feature.profile

import com.libraries.utils.Presenter

abstract class ProfilePresenter : Presenter<ProfileView>() {
    abstract fun onNavigateToEditProfile()
    abstract fun onNavigateToInvitations()
    abstract fun onLogout()
    abstract fun onRefresh()
}
