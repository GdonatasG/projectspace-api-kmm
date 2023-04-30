package com.project.space.feature.projects

import com.libraries.utils.Presenter

abstract class ProjectsPresenter : Presenter<ProjectsView>() {
    abstract fun onTabChange(tab: Tab)
    abstract fun onRetry()
}
