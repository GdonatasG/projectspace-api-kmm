package com.project.space.presentation.navigation

import androidx.navigation.NavOptions
import com.project.space.components.navigation.NavigationAction
import com.project.space.components.navigation.Navigator
import com.project.space.feature.authorization.AuthorizationPresenter
import com.project.space.presentation.destinations.AuthorizationScreenDestination
import com.project.space.presentation.destinations.MainScreenDestination
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


typealias SharedNavigationCoordinator = com.project.space.composition.navigation.Navigator

class DefaultSharedNavigationCoordinator(
    private val navigator: Navigator
) : SharedNavigationCoordinator {
    override fun startAuthorization(presenter: AuthorizationPresenter) {
        loadKoinModules(
            module {
                factory { presenter }
            }
        )

        navigator.navigate(
            NavigationAction(
                destination = AuthorizationScreenDestination(),
                navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build(),
                popUpAll = true
            )
        )
    }

    override fun startMain() {
        navigator.navigate(
            NavigationAction(
                destination = MainScreenDestination(),
                navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build(),
                popUpAll = true
            )
        )
    }

    override fun pop() {
        navigator.navigateBack()
    }

}
