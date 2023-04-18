package com.project.space.composition.navigation

import com.libraries.alerts.Alert
import com.project.space.composition.di.RootContainer

class AuthorizationFlow(
    private val container: RootContainer,
    private val navigator: Navigator,
    private val alert: Alert.Coordinator
) {
    fun start() {
        val container = container.authorization()
        val presenter = container.presenter(alert = alert) {
            // TODO: navigate to main
            println("AUTHORIZED!")
        }

        navigator.startAuthorization(presenter = presenter)
    }
}
