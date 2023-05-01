package com.project.space.feature.projects

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.libraries.utils.observer.Observer
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.projects.domain.GetProjects
import com.project.space.feature.projects.domain.GetSelectedProject
import com.project.space.feature.projects.domain.Project
import com.project.space.feature.projects.domain.SetSelectedProject

class DefaultProjectsPresenter(
    private val scope: PlatformScopeManager,
    private val getProjects: GetProjects,
    private val setProjectAsSelected: SetSelectedProject,
    private val getSelectedProject: GetSelectedProject,
    private val alert: Alert.Coordinator,
) : ProjectsPresenter() {
    override var viewHolder: ViewHolder<ProjectsView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var state: State = State.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var tab: Tab = Tab.MY_PROJECTS

    private var selectedProject: SelectedProject? = null

    override fun onAppear() {
        super.onAppear()
        selectedProject = getSelectedProject()
        state = State.Loading
    }

    override fun onResume() {
        super.onResume()
        _getProjects()
    }

    override fun onRetry() {
        state = State.Loading
        _getProjects()
    }

    override fun onRefresh() {
        state = State.Refreshing
        _getProjects()
    }

    override fun setSelectedProject(project: Project) {
        if (project.selected) return

        alert.show(Alert {
            title = "Change current project to ${project.name}?"
            buttons = listOf(Alert.Button.Cancel(), Alert.Button {
                title = "Change"
                event = Alert.Button.Event.DESTRUCTIVE
                onClick = {
                    setProjectAsSelected(project)
                    fetchSelectedProjectAndUpdateStateIfNeeded()
                }
            })
        })
    }

    private fun fetchSelectedProjectAndUpdateStateIfNeeded() {
        val newSelectedProject = getSelectedProject()
        if (selectedProject != newSelectedProject) {
            selectedProject = newSelectedProject
            updateSelectedProject()
        }
    }

    private fun _getProjects() {
        scope.cancelAllJobs()
        getProjects(tab = tab) { response ->
            when (response) {
                is GetProjects.Response.Success -> {
                    val projects = response.data

                    if (projects.isEmpty()) {
                        state = State.Empty(
                            title = "No projects found!",
                            message = "Feel free to refresh the list by tapping button below."
                        )

                        return@getProjects
                    }

                    state = State.Content(data = projects)
                    updateSelectedProject()
                }
                is GetProjects.Response.Error -> {
                    state = State.Error(title = "Unable to load projects!", message = response.message)
                }
            }
        }
    }

    private fun updateSelectedProject() {
        if (state !is State.Content) return

        state = State.Content(data = (state as State.Content).data.map { project ->
            project.copy(
                selected = selectedProject?.id == project.id
            )
        })
    }

    override fun onTabChange(tab: Tab) {
        this.tab = tab
        state = State.Loading
        _getProjects()
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }
}

enum class Tab {
    MY_PROJECTS, OTHERS
}
