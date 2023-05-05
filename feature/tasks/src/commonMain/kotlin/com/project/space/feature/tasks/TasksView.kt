package com.project.space.feature.tasks

import com.project.space.feature.tasks.domain.Task

sealed class State {
    object Loading : State()
    object Refreshing : State()
    data class Content(val data: List<Task>) : State()
    data class Empty(val title: String, val message: String) : State()
    data class Error(val title: String, val message: String) : State()
}

internal expect fun update(view: TasksView?, state: State)

expect interface TasksView
