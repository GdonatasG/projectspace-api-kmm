package com.project.space.composition.di.profile.usecase

import com.project.space.feature.common.AuthorizationStoreManager
import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.profile.domain.Logout

class LogoutUseCase(
    private val authorizationStoreManager: AuthorizationStoreManager,
    private val selectedProjectManager: SelectedProjectManager
) : Logout {
    override fun invoke() {
        authorizationStoreManager.clearAuthState()
        selectedProjectManager.clearSelectedProject()
    }
}
