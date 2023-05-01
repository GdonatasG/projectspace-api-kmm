package com.project.space.feature.projects

import com.libraries.utils.Presenter
import com.project.space.feature.projects.domain.Project

abstract class ProjectsPresenter : Presenter<ProjectsView>() {
    abstract fun onTabChange(tab: Tab)
    abstract fun onRetry()
    abstract fun onRefresh()
    abstract fun setSelectedProject(project: Project)

    abstract fun onNavigateToCreateProject()
}
