package com.project.space.feature.createproject.android_ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.createproject.CreateProjectPresenter
import com.project.space.feature.createproject.CreateProjectView
import com.project.space.feature.createproject.FormErrors
import com.project.space.feature.createproject.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateProjectViewModel(
    private val presenter: CreateProjectPresenter
) : ViewModel(), CreateProjectView, LifecycleEventObserver {
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Idle)
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private val _name: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val name: StateFlow<TextFieldValue> get() = _name.asStateFlow()

    private val _nameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val nameError: StateFlow<String?>
        get() = _nameError.asStateFlow()

    private val _description: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val description: StateFlow<TextFieldValue> get() = _description.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        _state.update { state.mapToViewState() }
    }

    override fun display(state: FormErrors) {
        _nameError.value = state.name
    }

    fun onNavigateBack() {
        presenter.onNavigateBack()
    }

    fun onNameChanged(value: TextFieldValue) {
        _name.value = value
    }

    fun onDescriptionChanged(value: TextFieldValue) {
        _description.value = value
    }

    fun onCreateProject() {
        presenter.onCreateProject(name = _name.value.text, description = _description.value.text)
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
        object Idle : ViewState()
        object Loading : ViewState()
    }

    private fun State.mapToViewState(): ViewState {
        return when (this) {
            State.Idle -> ViewState.Idle
            State.Loading -> ViewState.Loading
        }
    }
}
