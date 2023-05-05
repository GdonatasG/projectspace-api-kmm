package com.project.space.feature.tasks

import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.tasks.domain.GetTasks

class DefaultTasksPresenter(
    private val scope: PlatformScopeManager,
    private val getSelectedProject: (() -> SelectedProject?),
    private val getTasks: GetTasks,
    private val delegate: TasksDelegate
) : TasksPresenter() {
    override var viewHolder: ViewHolder<TasksView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var state: State = State.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var selectedProjectState: SelectedProjectState = SelectedProjectState.None(
        title = "No project selected!",
        message = "You do not have selected any project, feel free to select a project in Projects section below."
    )
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var tab: Tab = Tab.MY_TASKS

    override fun onResume() {
        super.onResume()
        val newSelectedProject = getSelectedProject()

        val currentSelectedProject: SelectedProject? = (selectedProjectState as? SelectedProjectState.Selected)?.project

        if (newSelectedProject != null) {
            selectedProjectState = SelectedProjectState.Selected(project = newSelectedProject)

            if (newSelectedProject != currentSelectedProject) {
                tab = Tab.MY_TASKS
                state = State.Loading
                _getTasks()
            }
            return
        }

        selectedProjectState = SelectedProjectState.None(
            title = "No project selected!",
            message = "You do not have selected any project, feel free to select a project in Projects section below."
        )
    }

    override fun onRetry() {
        state = State.Loading
        _getTasks()
    }

    override fun onRefresh() {
        state = State.Refreshing
        _getTasks()
    }

    override fun onNavigateToCreateTask() {
        delegate.onNavigateToCreateTask()
    }

    override fun onTabChange(tab: Tab) {
        this.tab = tab
        state = State.Loading
        _getTasks()
    }

    private fun _getTasks() {
        scope.cancelAllJobs()
        getTasks(tab = tab) { response ->
            when (response) {
                is GetTasks.Response.Success -> {
                    val tasks = response.data

                    if (tasks.isEmpty()) {
                        state = State.Empty(
                            title = "No tasks found!",
                            message = "Feel free to refresh the list by tapping button below."
                        )

                        return@getTasks
                    }

                    state = State.Content(data = tasks)
                }
                is GetTasks.Response.Error -> {
                    state = State.Error(title = "Unable to load tasks!", message = response.message)
                }
            }
        }
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }

}
