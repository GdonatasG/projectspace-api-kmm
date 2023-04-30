package com.project.space.feature.common

import com.libraries.preferences.Preferences
import com.libraries.preferences.getObject
import com.libraries.preferences.setObject
import com.libraries.utils.observer.Observable
import com.libraries.utils.observer.Observer
import com.project.space.feature.common.domain.model.SelectedProject

class DefaultSelectedProjectManager(private val preferences: Preferences) : SelectedProjectManager {
    override val observers: MutableSet<Observer<SelectedProject?>> = mutableSetOf()

    override fun add(observer: Observer<SelectedProject?>) {
        super.add(observer)
        observer.update(preferences.getObject(SELECTED_PROJECT))
    }

    override fun setSelectedProject(selectedProject: SelectedProject) {
        preferences.setObject(SELECTED_PROJECT, selectedProject)
        notifyObservers(selectedProject)
    }

    override fun clearSelectedProject() {
        preferences.remove(SELECTED_PROJECT)
        notifyObservers(null)
    }

    override fun getSelectedProject(): SelectedProject? = preferences.getObject(SELECTED_PROJECT)

    companion object {
        const val SELECTED_PROJECT = "SELECTED_PROJECT"
    }
}

interface SelectedProjectManager : Observable<SelectedProject?> {
    fun setSelectedProject(selectedProject: SelectedProject)
    fun clearSelectedProject()
    fun getSelectedProject(): SelectedProject?
}
