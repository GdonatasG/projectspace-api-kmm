package com.project.space.di

import com.project.space.SplashScope
import com.project.space.composition.di.RootContainer
import com.project.space.composition.di.dashboard.DashboardContainer
import com.project.space.composition.di.profile.ProfileContainer
import com.project.space.composition.di.projects.ProjectsContainer
import com.project.space.composition.di.tasks.TasksContainer
import com.project.space.composition.navigation.RootFlow
import com.project.space.feature.authorization.AuthorizationPresenter
import com.project.space.feature.authorization.android_ui.AuthorizationViewModel
import com.project.space.feature.common.FiltersViewModel
import com.project.space.feature.common.android_ui.FilterViewModel
import com.project.space.feature.createproject.CreateProjectPresenter
import com.project.space.feature.createproject.android_ui.CreateProjectViewModel
import com.project.space.feature.createtask.CreateTaskPresenter
import com.project.space.feature.createtask.android_ui.CreateTaskViewModel
import com.project.space.feature.dashboard.DashboardPresenter
import com.project.space.feature.dashboard.android_ui.DashboardViewModel
import com.project.space.feature.editprofile.EditProfilePresenter
import com.project.space.feature.editprofile.android_ui.EditProfileViewModel
import com.project.space.feature.profile.ProfilePresenter
import com.project.space.feature.profile.android_ui.ProfileViewModel
import com.project.space.feature.projects.ProjectsPresenter
import com.project.space.feature.projects.android_ui.ProjectsViewModel
import com.project.space.feature.splashscreen.SplashPresenter
import com.project.space.feature.splashscreen.android_ui.SplashViewModel
import com.project.space.feature.tasks.TasksPresenter
import com.project.space.feature.tasks.android_ui.TasksViewModel
import com.project.space.feature.userinvitations.UserInvitationsPresenter
import com.project.space.feature.userinvitations.android_ui.UserInvitationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonModule = module {
    single {
        RootContainer(
            navigator = get(), alert = get()
        )
    }

    factory {
        val flow = RootFlow(
            container = get(), navigator = get(), alert = get()
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

    viewModel {
        val presenter: CreateProjectPresenter = get()
        CreateProjectViewModel(presenter = presenter)
    }

    viewModel {
        val presenter: UserInvitationsPresenter = get()

        UserInvitationsViewModel(presenter = presenter)
    }

    viewModel {
        val presenter: EditProfilePresenter = get()

        EditProfileViewModel(presenter = presenter)
    }

    viewModel {
        val presenter: CreateTaskPresenter = get()

        CreateTaskViewModel(presenter = presenter)
    }

    viewModel {
        val filtersViewModel: FiltersViewModel = get()

        FilterViewModel(viewModel = filtersViewModel)
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

    factory<ProfileContainer> {
        val rootContainer: RootContainer = get()
        rootContainer.profile()
    }

    viewModel {
        val container: ProfileContainer = get()
        val presenter: ProfilePresenter = container.presenter(alert = get())

        ProfileViewModel(presenter = presenter)
    }

    factory<TasksContainer> {
        val rootContainer: RootContainer = get()
        rootContainer.tasks()
    }

    viewModel {
        val container: TasksContainer = get()
        val presenter: TasksPresenter = container.presenter(alert = get())

        TasksViewModel(presenter = presenter)
    }

    factory<DashboardContainer> {
        val rootContainer: RootContainer = get()

        rootContainer.dashboard()
    }

    viewModel {
        val container: DashboardContainer = get()
        val presenter: DashboardPresenter = container.presenter()

        DashboardViewModel(presenter = presenter)
    }
}
