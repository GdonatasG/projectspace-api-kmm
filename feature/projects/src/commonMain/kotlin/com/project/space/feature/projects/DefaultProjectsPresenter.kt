package com.project.space.feature.projects

import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.projects.domain.GetProjects

class DefaultProjectsPresenter(
    private val scope: PlatformScopeManager,
    private val getProjects: GetProjects
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

    override fun onAppear() {
        super.onAppear()
        _getProjects()
    }

    override fun onRetry() {
        _getProjects()
    }

    private fun _getProjects() {
        scope.cancelAllJobs()
        state = State.Loading
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
                }
                is GetProjects.Response.Error -> {
                    state = State.Error(title = "Unable to load projects!", message = response.message)
                }
            }
        }
    }

    override fun onTabChange(tab: Tab) {
        this.tab = tab
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
