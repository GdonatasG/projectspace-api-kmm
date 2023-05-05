package com.project.space.feature.tasks

import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.tasks.domain.GetTasks

class DefaultTasksPresenter(
    private val scope: PlatformScopeManager,
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

    private var tab: Tab = Tab.MY_TASKS

    override fun onAppear() {
        super.onAppear()
        state = State.Loading
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
                    val projects = response.data

                    if (projects.isEmpty()) {
                        state = State.Empty(
                            title = "No projects found!",
                            message = "Feel free to refresh the list by tapping button below."
                        )

                        return@getTasks
                    }

                    state = State.Content(data = projects)
                }
                is GetTasks.Response.Error -> {
                    state = State.Error(title = "Unable to load projects!", message = response.message)
                }
            }
        }
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }

}
