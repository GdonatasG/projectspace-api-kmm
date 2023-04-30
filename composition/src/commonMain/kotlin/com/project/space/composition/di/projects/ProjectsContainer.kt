package com.project.space.composition.di.projects

import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.di.RootContainer
import com.project.space.composition.di.projects.usecase.GetProjectsUseCase
import com.project.space.feature.projects.DefaultProjectsPresenter
import com.project.space.feature.projects.ProjectsPresenter
import com.project.space.feature.projects.domain.GetProjects
import com.project.space.services.project.ProjectService

class ProjectsContainer(
    private val projectService: ProjectService
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val getProjectsUseCase: GetProjects by lazy {
        GetProjectsUseCase(
            scope = scope,
            projectService = projectService
        )
    }

    fun presenter(): ProjectsPresenter = DefaultProjectsPresenter(
        scope = scope,
        getProjects = getProjectsUseCase
    )
}
