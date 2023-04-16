package com.project.space.feature.authorization

import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.authorization.domain.Login

class DefaultAuthorizationPresenter(
    private val scope: PlatformScopeManager = PlatformScopeManager(),
    private val login: Login,
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

        if (username.trim().isNotBlank() && password.trim().isNotBlank()) {
            state = State.Loading
            login(username = username, password = password) { response ->
                state = State.Idle
                when (response) {
                    is Login.Response.Success -> {
                        onAuthorized()
                    }
                    is Login.Response.Error -> {
                        println(response.message)
                    }
                }
            }
        }
    }
}
