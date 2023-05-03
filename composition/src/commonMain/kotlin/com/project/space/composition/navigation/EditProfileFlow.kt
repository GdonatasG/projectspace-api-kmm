package com.project.space.composition.navigation

import com.libraries.alerts.Alert
import com.project.space.composition.di.RootContainer
import com.project.space.feature.editprofile.EditProfileDelegate

class EditProfileFlow(
    private val container: RootContainer, private val navigator: Navigator, private val alert: Alert.Coordinator
) {
    fun start() {
        val container = container.editProfile()

        val presenter = container.presenter(
            alert = alert, delegate = DefaultEditProfileDelegate(
                navigator = navigator
            )
        )

        navigator.startEditProfile(presenter = presenter)
    }
}

private class DefaultEditProfileDelegate(
    private val navigator: Navigator
) : EditProfileDelegate {
    override fun onNavigateBack() {
        navigator.pop()
    }

}
