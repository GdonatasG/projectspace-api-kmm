package com.project.space.feature.inviteuser.android_ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.inviteuser.FormErrors
import com.project.space.feature.inviteuser.InviteUserPresenter
import com.project.space.feature.inviteuser.InviteUserView
import com.project.space.feature.inviteuser.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InviteUserViewModel(
    private val presenter: InviteUserPresenter
) : ViewModel(), InviteUserView, LifecycleEventObserver {
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Idle)
    val state: StateFlow<ViewState> get() = _state.asStateFlow()

    private val _email: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val email: StateFlow<TextFieldValue> get() = _email.asStateFlow()

    private val _emailError: MutableStateFlow<String?> = MutableStateFlow(null)
    val emailError: StateFlow<String?>
        get() = _emailError.asStateFlow()

    init {
        presenter.setView(this)
        presenter.onAppear()
    }

    override fun display(state: State) {
        _state.update { state.mapToViewState() }
    }

    override fun display(state: FormErrors) {
        _emailError.value = state.email
    }

    fun onNavigateBack() {
        presenter.onNavigateBack()
    }

    fun onEmailChanged(value: TextFieldValue) {
        _email.value = value
    }

    fun onInviteUser() {
        presenter.onInviteUser(email = _email.value.text)
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
