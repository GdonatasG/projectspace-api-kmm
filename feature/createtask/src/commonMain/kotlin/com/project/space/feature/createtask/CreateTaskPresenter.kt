package com.project.space.feature.createtask

import com.libraries.utils.Presenter

abstract class CreateTaskPresenter : Presenter<CreateTaskView>() {
    abstract fun onCreateTask(title: String, description: String, startDate: Double?, endDate: Double?)
    abstract fun onNavigateBack()
    abstract fun onNavigateToPrioritySelection()
}
