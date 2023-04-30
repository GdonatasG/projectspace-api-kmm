package com.project.space.feature.projects.domain

data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val selected: Boolean = false
)
