package com.project.space.feature.projects.domain

interface SetSelectedProject {
    operator fun invoke(project: Project)
}
