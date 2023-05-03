package com.project.space.feature.editprofile

import com.libraries.utils.Presenter

abstract class EditProfilePresenter : Presenter<EditProfileView>() {
    abstract fun onUpdateProfile(firstName: String, lastName: String, organizationName: String)
    abstract fun onNavigateBack()
}
