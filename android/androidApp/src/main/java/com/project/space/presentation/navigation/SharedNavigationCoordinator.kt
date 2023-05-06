package com.project.space.presentation.navigation

import androidx.navigation.NavOptions
import com.libraries.alerts.AlertController
import com.project.space.components.navigation.NavigationAction
import com.project.space.components.navigation.Navigator
import com.project.space.feature.authorization.AuthorizationPresenter
import com.project.space.feature.createproject.CreateProjectPresenter
import com.project.space.feature.createtask.CreateTaskPresenter
import com.project.space.feature.editprofile.EditProfilePresenter
import com.project.space.feature.userinvitations.UserInvitationsPresenter
import com.project.space.presentation.destinations.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


typealias SharedNavigationCoordinator = com.project.space.composition.navigation.Navigator

class DefaultSharedNavigationCoordinator(
    private val navigator: Navigator
) : SharedNavigationCoordinator {
    override fun startAuthorizationFromMain(presenter: AuthorizationPresenter) {
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
                    .setPopUpTo(MainScreenDestination.route, inclusive = true)
                    .build()
            )
        )
    }

    override fun startAuthorizationFromSplash(presenter: AuthorizationPresenter) {
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
                    .setPopUpTo(SplashScreenDestination.route, inclusive = true)
                    .build()
            )
        )
    }

    override fun startMainFromAuthorization() {
        navigator.navigate(
            NavigationAction(
                destination = MainScreenDestination(),
                navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(AuthorizationScreenDestination.route, inclusive = true)
                    .build()
            )
        )
    }

    override fun startMainFromSplash() {
        navigator.navigate(
            NavigationAction(
                destination = MainScreenDestination(),
                navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(SplashScreenDestination.route, inclusive = true)
                    .build()
            )
        )
    }

    override fun startCreateProject(presenter: CreateProjectPresenter) {
        loadKoinModules(
            module {
                factory { presenter }
            }
        )

        navigator.navigate(
            NavigationAction(
                destination = CreateProjectScreenDestination(),
                navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build(),
            )
        )
    }

    override fun startUserInvitations(presenter: UserInvitationsPresenter) {
        loadKoinModules(
            module {
                factory { presenter }
            }
        )

        navigator.navigate(
            NavigationAction(
                destination = UserInvitationsScreenDestination(),
                navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build(),
            )
        )
    }

    override fun startEditProfile(presenter: EditProfilePresenter) {
        loadKoinModules(
            module {
                factory { presenter }
            }
        )

        navigator.navigate(
            NavigationAction(
                destination = EditProfileScreenDestination(),
                navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build(),
            )
        )
    }

    override fun startCreateTask(presenter: CreateTaskPresenter) {
        loadKoinModules(
            module {
                factory { presenter }
            }
        )

        navigator.navigate(
            NavigationAction(
                destination = CreateTaskScreenDestination(),
                navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build(),
            )
        )
    }

    override fun pop() {
        navigator.navigateBack()
    }

}
