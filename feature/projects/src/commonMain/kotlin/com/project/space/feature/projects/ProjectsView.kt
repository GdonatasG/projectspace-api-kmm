package com.project.space.feature.projects

import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.projects.domain.Project

sealed class State {
    object Loading : State()
    object Refreshing : State()
    data class Content(val data: List<Project>) : State()
    data class Empty(val title: String, val message: String) : State()
    data class Error(val title: String, val message: String) : State()
}

internal expect fun update(view: ProjectsView?, state: State)

expect interface ProjectsView {
}
