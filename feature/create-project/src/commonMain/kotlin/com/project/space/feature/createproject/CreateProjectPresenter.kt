package com.project.space.feature.createproject

import com.libraries.utils.Presenter

abstract class CreateProjectPresenter : Presenter<CreateProjectView>() {
    abstract fun onCreateProject(name: String, description: String)
    abstract fun onNavigateBack()
}
