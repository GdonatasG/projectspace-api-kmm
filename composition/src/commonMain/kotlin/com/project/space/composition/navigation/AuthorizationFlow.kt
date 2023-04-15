package com.project.space.composition.navigation

import com.project.space.composition.di.RootContainer

class AuthorizationFlow(
    private val container: RootContainer,
    private val navigator: Navigator
) {
    fun start() {
        val container = container.authorization()
        val presenter = container.presenter {
            // TODO: navigate to main
            println("AUTHORIZED!")
        }

        navigator.startAuthorization(presenter = presenter)
    }
}
