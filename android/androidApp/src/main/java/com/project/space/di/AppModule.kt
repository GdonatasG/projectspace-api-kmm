package com.project.space.di

import com.project.space.SplashScope
import com.project.space.composition.di.RootContainer
import com.project.space.composition.di.projects.ProjectsContainer
import com.project.space.composition.navigation.RootFlow
import com.project.space.feature.authorization.AuthorizationPresenter
import com.project.space.feature.authorization.android_ui.AuthorizationViewModel
import com.project.space.feature.projects.ProjectsPresenter
import com.project.space.feature.projects.android_ui.ProjectsViewModel
import com.project.space.feature.splashscreen.SplashPresenter
import com.project.space.feature.splashscreen.android_ui.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonModule = module {
    single {
        RootContainer(
            navigator = get(),
            alert = get()
        )
    }

    factory {
        val flow = RootFlow(
            container = get(),
            navigator = get(),
            alert = get()
        )
        val scope = SplashScope(flow = flow)
        scope.createSplashPresenter()
    }

    viewModel {
        val presenter: SplashPresenter = get()
        SplashViewModel(presenter = presenter)
    }

    viewModel {
        val presenter: AuthorizationPresenter = get()
        AuthorizationViewModel(presenter = presenter)
    }
}

val bottomNavigationModule = module {
    factory<ProjectsContainer> {
        val rootContainer: RootContainer = get()
        rootContainer.projects()
    }

    viewModel {
        val container: ProjectsContainer = get()
        val presenter: ProjectsPresenter = container.presenter(alert = get())

        ProjectsViewModel(presenter = presenter)
    }
}
