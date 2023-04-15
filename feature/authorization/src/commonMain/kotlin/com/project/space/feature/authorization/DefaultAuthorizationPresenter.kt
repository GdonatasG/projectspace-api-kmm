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

    override fun onLogin() {

    }
}
