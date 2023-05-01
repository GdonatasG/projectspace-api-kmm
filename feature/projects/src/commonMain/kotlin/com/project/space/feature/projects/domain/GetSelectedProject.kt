package com.project.space.feature.projects.domain

import com.project.space.feature.common.domain.model.SelectedProject

interface GetSelectedProject {
    operator fun invoke(): SelectedProject?
}
