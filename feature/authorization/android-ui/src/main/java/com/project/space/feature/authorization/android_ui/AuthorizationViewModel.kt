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
    private val _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> get() = _selectedTabIndex.asStateFlow()

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Idle)
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private val _username: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val username: StateFlow<TextFieldValue> get() = _username.asStateFlow()

    private val _usernameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val usernameError: StateFlow<String?> = _usernameError.asStateFlow()

    private val _firstName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val firstName: StateFlow<TextFieldValue> get() = _firstName.asStateFlow()

    private val _firstNameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val firstNameError: StateFlow<String?> = _firstNameError.asStateFlow()

    private val _lastName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val lastName: StateFlow<TextFieldValue> get() = _lastName.asStateFlow()

    private val _lastNameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val lastNameError: StateFlow<String?> = _lastNameError.asStateFlow()

    private val _email: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val email: StateFlow<TextFieldValue> get() = _email.asStateFlow()

    private val _emailError: MutableStateFlow<String?> = MutableStateFlow(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _password: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val password: StateFlow<TextFieldValue> get() = _password.asStateFlow()

    private val _passwordError: MutableStateFlow<String?> = MutableStateFlow(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _passwordVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> get() = _passwordVisible.asStateFlow()

    private val _passwordRepeat: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val passwordRepeat: StateFlow<TextFieldValue> get() = _passwordRepeat.asStateFlow()

    private val _passwordRepeatError: MutableStateFlow<String?> = MutableStateFlow(null)
    val passwordRepeatError: StateFlow<String?> = _passwordRepeatError.asStateFlow()

    private val _passwordRepeatVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordRepeatVisible: StateFlow<Boolean> get() = _passwordRepeatVisible.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        _state.update { state.mapToViewState() }
    }

    private fun resetFormFields() {
        _username.value = TextFieldValue()
        _firstName.value = TextFieldValue()
        _lastName.value = TextFieldValue()
        _email.value = TextFieldValue()
        _password.value = TextFieldValue()
        _passwordVisible.value = false
        _passwordRepeat.value = TextFieldValue()
        _passwordRepeatVisible.value = false
    }

    override fun display(state: FormErrors) {
        _usernameError.value = state.usernameError
        _firstNameError.value = state.firstNameError
        _lastNameError.value = state.lastNameError
        _emailError.value = state.emailError
        _passwordError.value = state.passwordError
        _passwordRepeatError.value = state.passwordRepeatError
    }

    override fun onChangeModeToLogin() {
        resetFormFields()
        onModeChange(index = 0)
    }

    fun onLogin() {
        presenter.onLogin(username = username.value.text, password = password.value.text)
    }

    fun onRegister() {
        presenter.onRegister(
            username = username.value.text,
            firstName = _firstName.value.text,
            lastName = _lastName.value.text,
            email = _email.value.text,
            password = _password.value.text,
            passwordRepeat = _passwordRepeat.value.text
        )
    }

    fun onModeChange(index: Int) {
        _selectedTabIndex.value = index
    }

    fun onUsernameChanged(value: TextFieldValue) {
        _username.value = value
    }

    fun onFirstnameChanged(value: TextFieldValue) {
        _firstName.value = value
    }

    fun onLastnameChanged(value: TextFieldValue) {
        _lastName.value = value
    }

    fun onEmailChanged(value: TextFieldValue) {
        _email.value = value
    }

    fun onPasswordChanged(value: TextFieldValue) {
        _password.value = value
    }

    fun onPasswordVisibilityChanged(value: Boolean) {
        _passwordVisible.value = value
    }

    fun onPasswordRepeatChanged(value: TextFieldValue) {
        _passwordRepeat.value = value
    }

    fun onPasswordRepeatVisibilityChanged(value: Boolean) {
        _passwordRepeatVisible.value = value
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
