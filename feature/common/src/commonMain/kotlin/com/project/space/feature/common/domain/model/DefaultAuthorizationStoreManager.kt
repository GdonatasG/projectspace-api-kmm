package com.project.space.feature.common.domain.model

import com.libraries.preferences.Preferences
import com.libraries.preferences.getObject
import com.libraries.preferences.setObject

class DefaultAuthorizationStoreManager(private val preferences: Preferences) : AuthorizationStoreManager {
    override fun setAuthState(state: AuthorizationState) {
        preferences.setObject(AUTH_STATE, state)
    }

    override fun clearAuthState() = preferences.remove(AUTH_STATE)

    override fun getAuthState(): AuthorizationState? = preferences.getObject(AUTH_STATE)

    companion object {
        const val AUTH_STATE = "AUTH_STATE"
    }
}
