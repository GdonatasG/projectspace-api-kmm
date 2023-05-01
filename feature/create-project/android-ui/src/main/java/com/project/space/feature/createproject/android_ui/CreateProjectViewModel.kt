package com.project.space.feature.createproject.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.createproject.CreateProjectPresenter
import com.project.space.feature.createproject.CreateProjectView
import com.project.space.feature.createproject.FormErrors
import com.project.space.feature.createproject.State

class CreateProjectViewModel(
    private val presenter: CreateProjectPresenter
): ViewModel(), CreateProjectView, LifecycleEventObserver {

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        TODO("Not yet implemented")
    }

    override fun display(state: FormErrors) {
        TODO("Not yet implemented")
    }

    fun onNavigateBack(){
        presenter.onNavigateBack()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> presenter.onResume()
            Lifecycle.Event.ON_STOP -> presenter.onDisappear()

            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        presenter.dropView()
    }
}
