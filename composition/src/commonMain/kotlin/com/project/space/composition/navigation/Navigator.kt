package com.project.space.composition.navigation

import com.project.space.feature.authorization.AuthorizationPresenter
import com.project.space.feature.createproject.CreateProjectPresenter

interface Navigator {
    fun startAuthorization(presenter: AuthorizationPresenter)
    fun startMain()

    fun startCreateProject(presenter: CreateProjectPresenter)

    fun pop()
}
