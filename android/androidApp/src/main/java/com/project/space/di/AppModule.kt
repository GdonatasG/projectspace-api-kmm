package com.project.space.di

import com.project.space.SplashScope
import com.project.space.composition.di.RootContainer
import com.project.space.composition.navigation.RootFlow
import com.project.space.feature.splashscreen.SplashPresenter
import com.project.space.feature.splashscreen.android_ui.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonModule = module {
    single {
        RootContainer(
            navigator = get()
        )
    }

    factory {
        val flow = RootFlow(
            container = get(),
            navigator = get()
        )
        val scope = SplashScope(flow = flow)
        scope.createSplashPresenter()
    }

    viewModel {
        val presenter: SplashPresenter = get()
        SplashViewModel(presenter = presenter)
    }
}
