package com.project.space.di

import com.project.space.components.navigation.AppNavigator
import com.project.space.components.navigation.Navigator
import com.project.space.presentation.navigation.DefaultSharedNavigationCoordinator
import com.project.space.presentation.navigation.SharedNavigationCoordinator
import org.koin.dsl.module

val navigationCoordinatorsModule = module {
    single<Navigator> { AppNavigator() }
    single<SharedNavigationCoordinator> {
        DefaultSharedNavigationCoordinator(
            navigator = get()
        )
    }
}
