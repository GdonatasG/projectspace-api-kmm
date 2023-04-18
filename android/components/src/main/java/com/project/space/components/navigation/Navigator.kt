package com.project.space.components.navigation

import kotlinx.coroutines.flow.StateFlow

interface Navigator {
    fun navigate(action: NavigationAction)
    fun navigateBack()
    fun navigateToUrl(url: String)

    val navigateBackAction: StateFlow<Unit?>
    val navActions: StateFlow<NavigationAction?>
    val urlNavActions: StateFlow<String?>

    fun resetBackAction()
    fun resetNavigationAction()
    fun resetNavigateToUrlAction()
}
