package com.project.space.feature.projects.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.projects.ProjectsPresenter
import com.project.space.feature.projects.ProjectsView
import com.project.space.feature.projects.State
import com.project.space.feature.projects.Tab
import com.project.space.feature.projects.domain.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProjectsViewModel(
    private val presenter: ProjectsPresenter
) : ViewModel(), ProjectsView, LifecycleEventObserver {

    private val _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int>
        get() = _selectedTabIndex.asStateFlow()

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState>
        get() = _state.asStateFlow()

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean>
        get() = _refreshing.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        state.mapToViewState()?.let { newState ->
            _state.update { newState }
        }
    }

    fun onTabChange(index: Int) {
        if (_selectedTabIndex.value == index) return

        _selectedTabIndex.update { index }
        presenter.onTabChange(
            if (index == 0) Tab.MY_PROJECTS
            else Tab.OTHERS
        )
    }

    fun onRetry() {
        presenter.onRetry()
    }

    fun onRefresh() {
        presenter.onRefresh()
    }

    fun setSelectedProject(project: Project) {
        presenter.setSelectedProject(project)
    }

    fun onNavigateToCreateProject() {
        presenter.onNavigateToCreateProject()
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

    sealed class ViewState {
        object Loading : ViewState()
        data class Content(val data: List<Project>) : ViewState()
        data class Empty(val title: String, val message: String) : ViewState()
        data class Error(val title: String, val message: String) : ViewState()
    }

    private fun State.mapToViewState(): ViewState? = when (this) {
        is State.Loading -> ViewState.Loading
        is State.Refreshing -> {
            _refreshing.update { true }
            null
        }
        is State.Content -> {
            _refreshing.update { false }
            ViewState.Content(data = this.data)
        }
        is State.Empty -> ViewState.Empty(title = this.title, message = this.message)
        is State.Error -> ViewState.Error(title = this.title, message = this.message)
    }

}
