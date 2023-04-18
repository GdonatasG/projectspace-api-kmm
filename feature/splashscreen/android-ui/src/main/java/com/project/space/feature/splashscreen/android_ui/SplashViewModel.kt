package com.project.space.feature.splashscreen.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.splashscreen.SplashPresenter
import com.project.space.feature.splashscreen.SplashView

class SplashViewModel(
    private val presenter: SplashPresenter
) : ViewModel(), SplashView, LifecycleEventObserver {

    init {
        presenter.setView(this)
    }

    fun startAppFlow(){
        presenter.startAppFlow()
    }

    override fun onCleared() {
        super.onCleared()
        presenter.dropView()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> presenter.onAppear()
            Lifecycle.Event.ON_STOP -> presenter.onDisappear()
            else -> {}
        }
    }
}
