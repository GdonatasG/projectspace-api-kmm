package com.project.space.composition.di.profile.usecase

import com.project.space.feature.common.AuthorizationStoreManager
import com.project.space.feature.common.domain.model.CurrentUser
import com.project.space.feature.profile.domain.GetCurrentUser

class GetCurrentUserUseCase(
    private val authorizationStoreManager: AuthorizationStoreManager
) : GetCurrentUser {
    override fun invoke(): CurrentUser? = authorizationStoreManager.getCurrentUser()
}
