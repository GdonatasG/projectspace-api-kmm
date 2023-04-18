package com.project.space.feature.authorization

import com.libraries.utils.Presenter

abstract class AuthorizationPresenter : Presenter<AuthorizationView>() {
    abstract fun onLogin(username: String, password: String)

    abstract fun onRegister(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordRepeat: String,
    )
}
