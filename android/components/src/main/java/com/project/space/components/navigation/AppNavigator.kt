package com.project.space.components.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppNavigator : Navigator {
    private val _navigateBackAction: MutableStateFlow<Unit?> by lazy {
        MutableStateFlow(null)
    }

    private val _navAction: MutableStateFlow<NavigationAction?> by lazy {
        MutableStateFlow(null)
    }

    private val _urlNavAction: MutableStateFlow<String?> by lazy {
        MutableStateFlow(null)
    }

    override val urlNavActions: StateFlow<String?>
        get() = _urlNavAction.asStateFlow()

    override val navigateBackAction: StateFlow<Unit?>
        get() = _navigateBackAction.asStateFlow()

    override val navActions: StateFlow<NavigationAction?>
        get() = _navAction.asStateFlow()

    override fun navigate(action: NavigationAction) {
        _navAction.update { action }
    }

    override fun navigateBack() {
        _navigateBackAction.update { }
    }

    override fun navigateToUrl(url: String) {
        _urlNavAction.update { url }
    }

    override fun resetBackAction() {
        _navigateBackAction.value = null
    }

    override fun resetNavigationAction() {
        _navAction.value = null
    }

    override fun resetNavigateToUrlAction() {
        _urlNavAction.value = null
    }
}
