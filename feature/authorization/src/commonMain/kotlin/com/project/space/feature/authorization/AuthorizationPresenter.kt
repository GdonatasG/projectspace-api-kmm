package com.project.space.feature.authorization

import com.libraries.utils.Presenter

abstract class AuthorizationPresenter : Presenter<AuthorizationView>() {
    abstract fun onLogin()
}
