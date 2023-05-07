package com.project.space.composition.navigation

import com.libraries.alerts.Alert
import com.project.space.composition.di.RootContainer
import com.project.space.feature.inviteuser.InviteUserDelegate

class InviteUserFlow(
    private val container: RootContainer,
    private val navigator: Navigator,
    private val alert: Alert.Coordinator
) {
    fun start() {
        val container = container.inviteUser()

        val presenter = container.presenter(
            alert = alert,
            delegate = object : InviteUserDelegate {
                override fun onNavigateBack() {
                    navigator.pop()
                }

            }
        )

        navigator.startInviteUser(presenter = presenter)
    }
}
