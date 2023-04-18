package com.project.space.di

import com.libraries.alerts.*
import com.project.space.components.navigation.AppNavigator
import com.project.space.components.navigation.Navigator
import com.project.space.presentation.navigation.DefaultSharedNavigationCoordinator
import com.project.space.presentation.navigation.SharedNavigationCoordinator
import org.koin.dsl.module

val navigationCoordinatorsModule = module {
    single<AlertState> { AlertStateAdapter() }

    single<AlertController> { AlertControllerAdapter(alertState = get()) }

    single<Alert.Coordinator> { AlertCoordinator(alertController = get()) }

    single<Navigator> { AppNavigator() }
    single<SharedNavigationCoordinator> {
        DefaultSharedNavigationCoordinator(
            navigator = get()
        )
    }
}
