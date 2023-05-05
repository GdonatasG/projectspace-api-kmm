package com.project.space.feature.tasks.domain

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val endDate: String? = null
)
