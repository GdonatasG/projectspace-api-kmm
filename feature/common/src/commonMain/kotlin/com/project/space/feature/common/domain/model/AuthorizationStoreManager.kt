package com.project.space.feature.common.domain.model

interface AuthorizationStoreManager {
    fun setAuthState(state: AuthorizationState)
    fun clearAuthState()
    fun getAuthState(): AuthorizationState?
}
