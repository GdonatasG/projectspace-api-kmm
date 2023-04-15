package com.project.space

import com.project.space.composition.navigation.RootFlow
import com.project.space.feature.splashscreen.DefaultSplashPresenter
import com.project.space.feature.splashscreen.SplashPresenter

class SplashScope(
    private val flow: RootFlow
) {
    fun createSplashPresenter(): SplashPresenter {
        return DefaultSplashPresenter(
            onStartAppFlow = {
                flow.start()
            }
        )
    }
}
