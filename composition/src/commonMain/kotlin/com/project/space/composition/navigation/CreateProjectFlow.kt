package com.project.space.composition.navigation

import com.libraries.alerts.Alert
import com.project.space.composition.di.RootContainer
import com.project.space.feature.createproject.CreateProjectDelegate

class CreateProjectFlow(
    private val container: RootContainer,
    private val navigator: Navigator,
    private val alert: Alert.Coordinator
) {
    fun start() {
        val container = container.createProject()

        val presenter = container.presenter(
            alert = alert,
            delegate = DefaultCreateProjectDelegate(
                navigator = navigator
            )
        )

        navigator.startCreateProject(presenter = presenter)
    }
}

private class DefaultCreateProjectDelegate(
    private val navigator: Navigator
) : CreateProjectDelegate {
    override fun onNavigateBack() {
        navigator.pop()
    }
}
