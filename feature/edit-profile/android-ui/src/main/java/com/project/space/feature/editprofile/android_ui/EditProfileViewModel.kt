package com.project.space.feature.editprofile.android_ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.editprofile.*
import com.project.space.feature.editprofile.domain.CurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditProfileViewModel(
    private val presenter: EditProfilePresenter
) : ViewModel(), EditProfileView, LifecycleEventObserver {
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private val _updateState: MutableStateFlow<UpdateViewState> = MutableStateFlow(UpdateViewState.Idle)
    val updateState: StateFlow<UpdateViewState> get() = _updateState.asStateFlow()


    private val _username: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val username: StateFlow<TextFieldValue> get() = _username.asStateFlow()

    private val _firstName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val firstName: StateFlow<TextFieldValue> get() = _firstName.asStateFlow()

    private val _firstNameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val firstNameError: StateFlow<String?>
        get() = _firstNameError.asStateFlow()

    private val _lastName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val lastName: StateFlow<TextFieldValue> get() = _lastName.asStateFlow()

    private val _lastNameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val lastNameError: StateFlow<String?>
        get() = _lastNameError.asStateFlow()

    private val _email: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val email: StateFlow<TextFieldValue> get() = _email.asStateFlow()

    private val _organizationName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val organizationName: StateFlow<TextFieldValue> get() = _organizationName.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        _state.update { state.mapToViewState() }
    }

    override fun display(state: UpdateState) {
        _updateState.update { state.mapToViewState() }
    }

    override fun display(state: FormErrors) {
        _firstNameError.value = state.firstName
        _lastNameError.value = state.lastName
    }

    fun onNavigateBack() {
        presenter.onNavigateBack()
    }

    fun onRetry() {
        presenter.onAppear()
    }

    fun onFirstNameChanged(value: TextFieldValue) {
        _firstName.value = value
    }

    fun onLastNameChanged(value: TextFieldValue) {
        _lastName.value = value
    }

    fun onOrganizationNameChanged(value: TextFieldValue) {
        _organizationName.value = value
    }

    fun onUpdateProfile() {
        presenter.onUpdateProfile(
            firstName = _firstName.value.text,
            lastName = _lastName.value.text,
            organizationName = _organizationName.value.text
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
        object Loading : ViewState()
        data class Content(val user: CurrentUser) : ViewState()
        data class Error(val title: String, val message: String) : ViewState()
    }

    private fun State.mapToViewState(): ViewState {
        return when (this) {
            State.Loading -> ViewState.Loading
            is State.Content -> {
                _username.value = TextFieldValue(this.user.username)
                _firstName.value = TextFieldValue(this.user.firstName)
                _lastName.value = TextFieldValue(this.user.lastName)
                _email.value = TextFieldValue(this.user.email)
                _organizationName.value = TextFieldValue(this.user.organizationName)
                ViewState.Content(user = this.user)
            }
            is State.Error -> ViewState.Error(title = this.title, message = this.message)
        }
    }

    sealed class UpdateViewState {
        object Idle : UpdateViewState()
        object Loading : UpdateViewState()
    }

    private fun UpdateState.mapToViewState(): UpdateViewState {
        return when (this) {
            UpdateState.Idle -> UpdateViewState.Idle
            UpdateState.Loading -> UpdateViewState.Loading
        }
    }
}
