package com.project.space.feature.common.domain

import com.project.space.feature.common.domain.model.AuthorizationState
import com.project.space.feature.common.domain.model.CurrentUser

interface AuthorizationStoreManager {
    fun setAuthState(state: AuthorizationState)
    fun clearAuthState()
    fun getAuthState(): AuthorizationState?
    fun setCurrentUser(currentUser: CurrentUser)
    fun clearCurrentUser()
    fun getCurrentUser(): CurrentUser?
}
