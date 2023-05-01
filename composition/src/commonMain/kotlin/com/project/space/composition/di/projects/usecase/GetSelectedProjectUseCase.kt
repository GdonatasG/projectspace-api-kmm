package com.project.space.composition.di.projects.usecase

import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.projects.domain.GetSelectedProject

class GetSelectedProjectUseCase(
    private val selectedProjectManager: SelectedProjectManager
) : GetSelectedProject {
    override fun invoke(): SelectedProject? = selectedProjectManager.getSelectedProject()
}
