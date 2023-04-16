package com.project.space.feature.common.domain

import com.project.space.feature.common.domain.model.AuthorizationState
import com.project.space.feature.common.domain.model.CurrentUser
import com.project.space.feature.common.domain.model.SelectedProject

interface AuthorizationStoreManager {
    fun setAuthState(state: AuthorizationState)
    fun clearAuthState()
    fun getAuthState(): AuthorizationState?
    fun setCurrentUser(currentUser: CurrentUser)
    fun clearCurrentUser()
    fun getCurrentUser(): CurrentUser?
    fun setSelectedProject(selectedProject: SelectedProject)
    fun clearSelectedProject()
    fun getSelectedProject(): SelectedProject?
}
