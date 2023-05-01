package com.project.space.composition.navigation

import com.project.space.feature.authorization.AuthorizationPresenter
import com.project.space.feature.createproject.CreateProjectPresenter

interface Navigator {
    fun startAuthorizationFromMain(presenter: AuthorizationPresenter)

    fun startAuthorizationFromSplash(presenter: AuthorizationPresenter)
    fun startMainFromAuthorization()

    fun startMainFromSplash()

    fun startCreateProject(presenter: CreateProjectPresenter)

    fun pop()
}
