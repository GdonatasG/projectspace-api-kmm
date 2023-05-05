package com.project.space.feature.tasks

import com.libraries.utils.Presenter

abstract class TasksPresenter : Presenter<TasksView>() {
    abstract fun onTabChange(tab: Tab)
    abstract fun onRetry()
    abstract fun onRefresh()
    abstract fun onNavigateToCreateTask()
}

enum class Tab {
    MY_TASKS, ALL_TASKS
}
