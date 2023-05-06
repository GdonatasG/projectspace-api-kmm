package com.project.space.feature.createtask.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.createtask.*

class CreateTaskViewModel(
    private val presenter: CreateTaskPresenter
) : ViewModel(), CreateTaskView, LifecycleEventObserver {

    override fun display(state: State) {

    }

    override fun display(state: PriorityState) {

    }

    override fun display(state: FormErrors) {

    }

    override fun display(state: SelectedProjectState) {

    }

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    fun onNavigateBack() {
        presenter.onNavigateBack()
    }

    fun onNavigateToPrioritySelection(){
        presenter.onNavigateToPrioritySelection()
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
