package com.project.space.feature.authorization.android_ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.authorization.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthorizationViewModel(
    private val presenter: AuthorizationPresenter
) : ViewModel(), AuthorizationView, LifecycleEventObserver {
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Idle)
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private val _mode: MutableStateFlow<ViewMode> = MutableStateFlow(ViewMode.Login)
    val mode: StateFlow<ViewMode> get() = _mode.asStateFlow()

    private val _username: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val username: StateFlow<TextFieldValue> get() = _username.asStateFlow()

    private val _usernameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val usernameError: StateFlow<String?> = _usernameError.asStateFlow()

    private val _password: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val password: StateFlow<TextFieldValue> get() = _password.asStateFlow()

    private val _passwordError: MutableStateFlow<String?> = MutableStateFlow(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        _state.update { state.mapToViewState() }
    }

    override fun display(state: Mode) {
        _mode.update { state.mapToView() }
    }

    private fun resetFormFields() {
        _username.value = TextFieldValue()
        _password.value = TextFieldValue()
    }

    override fun display(state: FormErrors) {
        _usernameError.value = state.usernameError
        _passwordError.value = state.passwordError
    }

    fun onLogin() {
        presenter.onLogin(username = username.value.text, password = password.value.text)
    }

    fun onModeChange(mode: ViewMode) {
        presenter.onModeChange(mode = if (mode == ViewMode.Login) Mode.Login else Mode.Register)
        resetFormFields()
    }

    fun onUsernameChanged(value: TextFieldValue) {
        _username.value = value
    }

    fun onPasswordChanged(value: TextFieldValue) {
        _password.value = value
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

    sealed class ViewMode(val title: String) {
        object Login : ViewMode(title = "Login")
        object Register : ViewMode(title = "Register")
    }

    private fun Mode.mapToView(): ViewMode {
        return when (this) {
            Mode.Login -> ViewMode.Login
            Mode.Register -> ViewMode.Register
        }
    }
}
