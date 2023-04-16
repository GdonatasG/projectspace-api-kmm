package com.project.space.feature.common.domain

import com.libraries.preferences.Preferences
import com.libraries.preferences.getObject
import com.libraries.preferences.setObject
import com.project.space.feature.common.domain.model.AuthorizationState
import com.project.space.feature.common.domain.model.CurrentUser
import com.project.space.feature.common.domain.model.SelectedProject

class DefaultAuthorizationStoreManager(private val preferences: Preferences) : AuthorizationStoreManager {
    override fun setAuthState(state: AuthorizationState) {
        preferences.setObject(AUTH_STATE, state)
    }

    override fun clearAuthState() = preferences.remove(AUTH_STATE)

    override fun getAuthState(): AuthorizationState? = preferences.getObject(AUTH_STATE)
    override fun setCurrentUser(currentUser: CurrentUser) {
        preferences.setObject(CURRENT_USER, currentUser)
    }

    override fun clearCurrentUser() = preferences.remove(CURRENT_USER)

    override fun getCurrentUser(): CurrentUser? = preferences.getObject(CURRENT_USER)
    override fun setSelectedProject(selectedProject: SelectedProject) {
        preferences.setObject(SELECTED_PROJECT, selectedProject)
    }

    override fun clearSelectedProject() = preferences.remove(SELECTED_PROJECT)

    override fun getSelectedProject(): SelectedProject? = preferences.getObject(SELECTED_PROJECT)

    companion object {
        const val AUTH_STATE = "AUTH_STATE"
        const val CURRENT_USER = "CURRENT_USER"
        const val SELECTED_PROJECT = "SELECTED_PROJECT"
    }
}
