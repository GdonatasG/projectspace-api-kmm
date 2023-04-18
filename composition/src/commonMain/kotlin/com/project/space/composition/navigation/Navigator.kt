package com.project.space.composition.navigation

import com.project.space.feature.authorization.AuthorizationPresenter

interface Navigator {
    fun startAuthorization(presenter: AuthorizationPresenter)

    fun pop()
}
