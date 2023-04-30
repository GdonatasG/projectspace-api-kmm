package com.project.space.composition.di.projects

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.observer.Observable
import com.project.space.composition.di.RootContainer
import com.project.space.composition.di.projects.usecase.GetProjectsUseCase
import com.project.space.composition.di.projects.usecase.SetSelectedProjectUseCase
import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.projects.DefaultProjectsPresenter
import com.project.space.feature.projects.ProjectsPresenter
import com.project.space.feature.projects.domain.GetProjects
import com.project.space.feature.projects.domain.SetSelectedProject
import com.project.space.services.project.ProjectService

class ProjectsContainer(
    private val projectService: ProjectService,
    private val selectedProjectManager: SelectedProjectManager,
    private val selectedProjectObservable: Observable<SelectedProject?>
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val getProjectsUseCase: GetProjects by lazy {
        GetProjectsUseCase(
            scope = scope,
            projectService = projectService
        )
    }

    private val setSelectedProject: SetSelectedProject by lazy {
        SetSelectedProjectUseCase(
            selectedProjectManager = selectedProjectManager
        )
    }

    fun presenter(alert: Alert.Coordinator): ProjectsPresenter = DefaultProjectsPresenter(
        scope = scope,
        getProjects = getProjectsUseCase,
        setProject = setSelectedProject,
        alert = alert,
        selectedProjectObservable = selectedProjectObservable
    )
}
