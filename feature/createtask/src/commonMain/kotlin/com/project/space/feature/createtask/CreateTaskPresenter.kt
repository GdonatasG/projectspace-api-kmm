package com.project.space.feature.createtask

import com.libraries.utils.Presenter

abstract class CreateTaskPresenter : Presenter<CreateTaskView>() {
    abstract fun onCreateTask(title: String, description: String, startDate: String?, endDate: String?)
    abstract fun onNavigateBack()
    abstract fun onNavigateToPrioritySelection()
}
