package com.project.space.composition.di.editprofile

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.di.editprofile.usecase.EditProfileUseCase
import com.project.space.composition.di.editprofile.usecase.FetchCurrentUserUseCase
import com.project.space.composition.navigation.Navigator
import com.project.space.feature.editprofile.DefaultEditProfilePresenter
import com.project.space.feature.editprofile.EditProfileDelegate
import com.project.space.feature.editprofile.EditProfilePresenter
import com.project.space.feature.editprofile.domain.EditProfile
import com.project.space.feature.editprofile.domain.FetchCurrentUser
import com.project.space.services.user.UserService

class EditProfileContainer(
    private val navigator: Navigator, private val userService: UserService
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val editProfileUseCase: EditProfile by lazy {
        EditProfileUseCase(
            scope = scope, userService = userService
        )
    }

    private val fetchCurrentUserUseCase: FetchCurrentUser by lazy {
        FetchCurrentUserUseCase(
            scope = scope, userService = userService
        )
    }

    fun presenter(alert: Alert.Coordinator, delegate: EditProfileDelegate): EditProfilePresenter = DefaultEditProfilePresenter(
        scope = scope, delegate = delegate, alert = alert, editProfile = editProfileUseCase, fetchCurrentUser = fetchCurrentUserUseCase
    )
}
