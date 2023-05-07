package com.project.space.feature.inviteuser

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.inviteuser.domain.InviteUser

class DefaultInviteUserPresenter(
    private val scope: PlatformScopeManager,
    private val delegate: InviteUserDelegate,
    private val alert: Alert.Coordinator,
    private val inviteUser: InviteUser
) : InviteUserPresenter() {
    override var viewHolder: ViewHolder<InviteUserView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var state: State = State.Idle
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var formErrors: FormErrors = FormErrors.empty()
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    override fun onNavigateBack() {
        delegate.onNavigateBack()
    }

    override fun onInviteUser(email: String) {
        formErrors = FormErrors.empty()

        if (!isEmailValid(email)) {
            formErrors = formErrors.copy(email = "Email is not valid!")
        }

        if (formErrors.isValid()) {
            state = State.Loading
            inviteUser(email = email) { response ->
                state = State.Idle
                when (response) {
                    is InviteUser.Response.Success -> {
                        onNavigateBack()
                        alert.show(Alert {
                            title = "User \"$email\" successfully invited!"
                            buttons = listOf(Alert.Button {
                                title = "Ok"
                                event = Alert.Button.Event.DESTRUCTIVE
                                onClick = {}
                            })
                        })
                    }
                    is InviteUser.Response.InputErrors -> {
                        response.data.forEach { error ->
                            when (error.input) {
                                "entity" -> {
                                    alert.show(Alert {
                                        title = "Failed!"
                                        message = error.message
                                        buttons = listOf(Alert.Button {
                                            title = "Ok"
                                            event = Alert.Button.Event.DESTRUCTIVE
                                            onClick = {}
                                        })
                                    })
                                }
                                "email" -> {
                                    formErrors = formErrors.copy(email = error.message)
                                }
                            }
                        }
                    }
                    is InviteUser.Response.Error -> {
                        alert.show(Alert {
                            title = "Failed!"
                            message = response.message
                            buttons = listOf(Alert.Button {
                                title = "Ok"
                                event = Alert.Button.Event.DESTRUCTIVE
                                onClick = {}
                            })
                        })
                    }
                }
            }
        }
    }


    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }
}
