package com.project.space.composition.di.tasks

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.di.RootContainer
import com.project.space.composition.di.tasks.usecase.GetTasksUseCase
import com.project.space.composition.navigation.CreateTaskFlow
import com.project.space.composition.navigation.Navigator
import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.tasks.DefaultTasksPresenter
import com.project.space.feature.tasks.TasksDelegate
import com.project.space.feature.tasks.TasksPresenter
import com.project.space.feature.tasks.domain.GetTasks
import com.project.space.services.task.TaskService

class TasksContainer(
    private val navigator: Navigator,
    private val container: RootContainer,
    private val taskService: TaskService,
    private val selectedProjectManager: SelectedProjectManager
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val getTasksUseCase: GetTasks by lazy {
        GetTasksUseCase(
            scope = scope,
            selectedProjectManager = selectedProjectManager,
            taskService = taskService,
        )
    }

    fun presenter(alert: Alert.Coordinator): TasksPresenter = DefaultTasksPresenter(
        scope = scope,
        getSelectedProject = {
            selectedProjectManager.getSelectedProject()
        },
        getTasks = getTasksUseCase,
        delegate = DefaultTasksDelegate(
            navigator = navigator,
            container = container,
            alert = alert
        )
    )
}

private class DefaultTasksDelegate(
    private val navigator: Navigator,
    private val container: RootContainer,
    private val alert: Alert.Coordinator
) : TasksDelegate {
    override fun onNavigateToCreateTask() {
        CreateTaskFlow(
            container = container,
            navigator = navigator,
            alert = alert
        ).start()
    }
}
