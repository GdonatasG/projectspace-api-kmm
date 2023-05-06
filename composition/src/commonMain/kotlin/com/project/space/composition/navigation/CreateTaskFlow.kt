package com.project.space.composition.navigation

import com.libraries.alerts.Alert
import com.project.space.composition.di.RootContainer
import com.project.space.feature.common.FiltersViewModel
import com.project.space.feature.createtask.CreateTaskDelegate

class CreateTaskFlow(
    private val container: RootContainer,
    private val navigator: Navigator,
    private val alert: Alert.Coordinator
) {
    fun start() {
        val container = container.createTask()

        val presenter = container.presenter(
            alert = alert,
            delegate = DefaultCreateTaskDelegate(
                navigator = navigator
            )
        )

        navigator.startCreateTask(presenter = presenter)
    }
}

private class DefaultCreateTaskDelegate(
    private val navigator: Navigator
) : CreateTaskDelegate {
    override fun onNavigateBack() {
        navigator.pop()
    }

    override fun onNavigateToFilter(filter: FiltersViewModel) {
        TODO("Not yet implemented")
    }

}
