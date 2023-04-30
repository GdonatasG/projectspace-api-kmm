package com.project.space.composition.di.projects

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.projects.DefaultProjectsPresenter
import com.project.space.feature.projects.ProjectsPresenter

class ProjectsContainer {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    fun presenter(): ProjectsPresenter = DefaultProjectsPresenter(
        scope = scope
    )
}
