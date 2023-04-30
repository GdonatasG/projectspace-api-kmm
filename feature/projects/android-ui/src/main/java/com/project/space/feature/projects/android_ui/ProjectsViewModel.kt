package com.project.space.feature.projects.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.projects.ProjectsPresenter
import com.project.space.feature.projects.ProjectsView

class ProjectsViewModel(
    private val presenter: ProjectsPresenter
) : ViewModel(), ProjectsView, LifecycleEventObserver {

    init {
        presenter.setView(this)
        presenter.onAppear()
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
