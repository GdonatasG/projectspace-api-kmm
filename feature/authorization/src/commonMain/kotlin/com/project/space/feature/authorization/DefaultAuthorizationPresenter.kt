package com.project.space.feature.authorization

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.authorization.domain.Login
import com.project.space.feature.authorization.domain.Register

class DefaultAuthorizationPresenter(
    private val scope: PlatformScopeManager = PlatformScopeManager(),
    private val alert: Alert.Coordinator,
    private val login: Login,
    private val register: Register,
    private val onAuthorized: () -> Unit
) : AuthorizationPresenter() {
    override var viewHolder: ViewHolder<AuthorizationView> = ViewHolder()

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

    override fun onAppear() {
        super.onAppear()
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }

    override fun onLogin(username: String, password: String) {
        formErrors = FormErrors.empty()
        if (username.trim().isBlank()) {
            formErrors = formErrors.copy(usernameError = "Username must not be empty!")
        }

        if (password.trim().isBlank()) {
            formErrors = formErrors.copy(passwordError = "Password must not be empty!")
        }

        if (formErrors.isValid()) {
            state = State.Loading
            login(username = username, password = password) { response ->
                state = State.Idle
                when (response) {
                    is Login.Response.Success -> {
                        onAuthorized()
                    }
                    is Login.Response.Error -> {
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

    override fun onRegister(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordRepeat: String
    ) {
        formErrors = FormErrors.empty()
        if (username.trim().isBlank()) {
            formErrors = formErrors.copy(usernameError = "Username must not be empty!")
        }

        if (firstName.trim().isBlank()) {
            formErrors = formErrors.copy(firstNameError = "Firstname must not be empty!")
        }

        if (lastName.trim().isBlank()) {
            formErrors = formErrors.copy(lastNameError = "Firstname must not be empty!")
        }

        if (!isEmailValid(email)) {
            formErrors = formErrors.copy(emailError = "Email is not valid!")
        }

        if (password.trim() != password) {
            formErrors = formErrors.copy(passwordError = "Password must not contain whitespaces!")
        }

        if (password.trim().isBlank()) {
            formErrors = formErrors.copy(passwordError = "Password must not be empty!")
        }

        if (password != passwordRepeat) {
            formErrors = formErrors.copy(passwordRepeatError = "Passwords does not match!")
        }

        if (formErrors.isValid()) {
            state = State.Loading

            register(
                username = username,
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            ) { response ->
                state = State.Idle
                when (response) {
                    is Register.Response.Success -> {
                        view?.onChangeModeToLogin()
                        alert.show(Alert {
                            title = "Registration successful!"
                            message = "You have successfully registered. Now you can login."
                            buttons = listOf(Alert.Button {
                                title = "Ok"
                                event = Alert.Button.Event.DESTRUCTIVE
                                onClick = {}
                            })
                        })
                    }
                    is Register.Response.InputErrors -> {
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
                                "username" -> {
                                    formErrors = formErrors.copy(usernameError = error.message)
                                }
                                "first_name" -> {
                                    formErrors = formErrors.copy(firstNameError = error.message)
                                }
                                "last_name" -> {
                                    formErrors = formErrors.copy(lastNameError = error.message)
                                }
                                "email" -> {
                                    formErrors = formErrors.copy(emailError = error.message)
                                }
                                "password" -> {
                                    formErrors = formErrors.copy(passwordError = error.message)
                                }
                            }
                        }
                    }
                    is Register.Response.Error -> {
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

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }

}
