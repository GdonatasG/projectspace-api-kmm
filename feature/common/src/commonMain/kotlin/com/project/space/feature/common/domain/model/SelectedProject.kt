package com.project.space.feature.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SelectedProject(
    val id: Int,
    val name: String
)
