package com.project.space.feature.splashscreen

import com.libraries.utils.Presenter

abstract class SplashPresenter : Presenter<SplashView>() {
    abstract fun startAppFlow()
}
