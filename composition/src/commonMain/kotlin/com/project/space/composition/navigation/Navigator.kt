package com.project.space.composition.navigation

import com.project.space.feature.authorization.AuthorizationPresenter
import com.project.space.feature.common.FiltersViewModel
import com.project.space.feature.createproject.CreateProjectPresenter
import com.project.space.feature.createtask.CreateTaskPresenter
import com.project.space.feature.editprofile.EditProfilePresenter
import com.project.space.feature.inviteuser.InviteUserPresenter
import com.project.space.feature.userinvitations.UserInvitationsPresenter

interface Navigator {
    fun startAuthorizationFromMain(presenter: AuthorizationPresenter)

    fun startAuthorizationFromSplash(presenter: AuthorizationPresenter)
    fun startMainFromAuthorization()

    fun startMainFromSplash()

    fun startCreateProject(presenter: CreateProjectPresenter)
    fun startUserInvitations(presenter: UserInvitationsPresenter)

    fun startEditProfile(presenter: EditProfilePresenter)

    fun startCreateTask(presenter: CreateTaskPresenter)

    fun startFilter(viewModel: FiltersViewModel)

    fun startInviteUser(presenter: InviteUserPresenter)

    fun pop()
}
