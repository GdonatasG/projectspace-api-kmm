package com.project.space.composition.navigation

import com.libraries.alerts.Alert
import com.project.space.composition.di.RootContainer

class RootFlow(
    private val container: RootContainer,
    private val navigator: Navigator,
    private val alert: Alert.Coordinator
) {
    fun start() {
        if (container.session == null) {
            return AuthorizationFlow(
                container = container,
                navigator = navigator,
                alert = alert
            ).start()
        }

        navigator.startMainFromSplash()
    }
}
