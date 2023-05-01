package com.project.space.composition.di.createproject

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.createproject.CreateProjectDelegate
import com.project.space.feature.createproject.CreateProjectPresenter
import com.project.space.feature.createproject.DefaultCreateProjectPresenter

class CreateProjectContainer(
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    fun presenter(delegate: CreateProjectDelegate): CreateProjectPresenter = DefaultCreateProjectPresenter(
        delegate = delegate
    )
}
