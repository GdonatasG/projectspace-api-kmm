package com.project.space.composition.di.projects.usecase

import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.projects.domain.Project
import com.project.space.feature.projects.domain.SetSelectedProject

class SetSelectedProjectUseCase(
    private val selectedProjectManager: SelectedProjectManager
) : SetSelectedProject {
    override fun invoke(project: Project) {
        selectedProjectManager.setSelectedProject(project.toData())
    }
}

private fun Project.toData(): SelectedProject = SelectedProject(
    id = this.id,
    name = this.name
)
