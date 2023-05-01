package com.project.space.feature.common

import com.libraries.preferences.Preferences
import com.libraries.preferences.getObject
import com.libraries.preferences.setObject
import com.project.space.feature.common.domain.model.SelectedProject

class DefaultSelectedProjectManager(private val preferences: Preferences) : SelectedProjectManager {
    override fun setSelectedProject(selectedProject: SelectedProject) {
        preferences.setObject(SELECTED_PROJECT, selectedProject)
    }

    override fun clearSelectedProject() {
        preferences.remove(SELECTED_PROJECT)
    }

    override fun getSelectedProject(): SelectedProject? = preferences.getObject(SELECTED_PROJECT)

    companion object {
        const val SELECTED_PROJECT = "SELECTED_PROJECT"
    }
}

interface SelectedProjectManager {
    fun setSelectedProject(selectedProject: SelectedProject)
    fun clearSelectedProject()
    fun getSelectedProject(): SelectedProject?
}
