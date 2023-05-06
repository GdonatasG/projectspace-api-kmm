package com.project.space.feature.createtask.android_ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.libraries.utils.DateTimeFormatter
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.createtask.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class CreateTaskViewModel(
    private val presenter: CreateTaskPresenter
) : ViewModel(), CreateTaskView, LifecycleEventObserver {
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Idle)
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private val _selectedProjectState: MutableStateFlow<SelectedProjectViewState> =
        MutableStateFlow(SelectedProjectViewState.None(title = "", message = ""))
    val selectedProjectState: StateFlow<SelectedProjectViewState> get() = _selectedProjectState.asStateFlow()

    private val _title: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val title: StateFlow<TextFieldValue> get() = _title.asStateFlow()

    private val _titleError: MutableStateFlow<String?> = MutableStateFlow(null)
    val titleError: StateFlow<String?>
        get() = _titleError.asStateFlow()

    private val _description: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val description: StateFlow<TextFieldValue> get() = _description.asStateFlow()

    private val _priority: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val priority: StateFlow<TextFieldValue> get() = _priority.asStateFlow()

    private val _priorityError: MutableStateFlow<String?> = MutableStateFlow(null)
    val priorityError: StateFlow<String?>
        get() = _priorityError.asStateFlow()

    private val _selectedStartDate: MutableStateFlow<LocalDate?> = MutableStateFlow(null)
    val selectedStartDate: StateFlow<LocalDate?>
        get() = _selectedStartDate.asStateFlow()

    private val _selectedEndDate: MutableStateFlow<LocalDate?> = MutableStateFlow(null)
    val selectedEndDate: StateFlow<LocalDate?>
        get() = _selectedEndDate.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        _state.update { state.mapToViewState() }
    }

    override fun display(state: PriorityState) {
        when (state) {
            is PriorityState.Selected -> {
                _priority.value = TextFieldValue(text = state.priority.name)
            }
            is PriorityState.None -> {
                _priority.value = TextFieldValue()
            }
        }
    }

    override fun display(state: FormErrors) {
        _titleError.value = state.title
        _priorityError.value = state.priority
    }

    override fun display(state: SelectedProjectState) {
        _selectedProjectState.update { state.mapToViewState() }
    }

    fun onTitleChanged(value: TextFieldValue) {
        _title.value = value
    }

    fun onDescriptionChanged(value: TextFieldValue) {
        _description.value = value
    }

    fun onStartDateChanged(date: LocalDate?) {
        _selectedStartDate.value = date
        _selectedEndDate.value?.let { end ->
            if (end < date) {
                _selectedEndDate.value = null
            }
        }
    }

    fun onEndDateChanged(date: LocalDate?) {
        _selectedEndDate.value = date
    }

    fun onNavigateBack() {
        presenter.onNavigateBack()
    }

    fun onNavigateToPrioritySelection() {
        presenter.onNavigateToPrioritySelection()
    }

    fun onCreateTask() {
        var startDateString: String? = null
        _selectedStartDate.value?.let {
            startDateString = java.time.format.DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .format(it)
        }

        var endDateString: String? = null
        _selectedEndDate.value?.let {
            endDateString = java.time.format.DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .format(it)
        }

        presenter.onCreateTask(
            title = _title.value.text,
            description = _description.value.text,
            startDate = startDateString,
            endDate = endDateString
        )
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

    sealed class SelectedProjectViewState {
        data class None(val title: String, val message: String) : SelectedProjectViewState()
        data class Selected(val project: SelectedProject) : SelectedProjectViewState()
    }

    private fun SelectedProjectState.mapToViewState(): SelectedProjectViewState {
        return when (this) {
            is SelectedProjectState.None -> SelectedProjectViewState.None(title = this.title, message = this.message)
            is SelectedProjectState.Selected -> SelectedProjectViewState.Selected(project = this.project)
        }
    }

}
