package com.project.space.composition.di.createproject

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.di.createproject.usecase.CreateProjectUseCase
import com.project.space.feature.createproject.CreateProjectDelegate
import com.project.space.feature.createproject.CreateProjectPresenter
import com.project.space.feature.createproject.DefaultCreateProjectPresenter
import com.project.space.feature.createproject.domain.CreateProject
import com.project.space.services.project.ProjectService

class CreateProjectContainer(
    private val projectService: ProjectService
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val createProjectUseCase: CreateProject by lazy {
        CreateProjectUseCase(
            scope = scope,
            projectService = projectService
        )
    }

    fun presenter(alert: Alert.Coordinator, delegate: CreateProjectDelegate): CreateProjectPresenter =
        DefaultCreateProjectPresenter(
            scope = scope,
            alert = alert,
            delegate = delegate,
            createProject = createProjectUseCase
        )
}
