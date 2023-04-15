package com.project.space.composition.navigation

import com.project.space.composition.di.RootContainer

class RootFlow(
    private val container: RootContainer,
    private val navigator: Navigator
) {
    fun start() {
        if (container.session == null) {
            return AuthorizationFlow(
                container = container,
                navigator = navigator
            ).start()
        }

        // TODO: start main
    }
}
