package com.project.space.feature.editprofile

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.editprofile.domain.EditProfile
import com.project.space.feature.editprofile.domain.FetchCurrentUser

class DefaultEditProfilePresenter(
    private val scope: PlatformScopeManager,
    private val delegate: EditProfileDelegate,
    private val alert: Alert.Coordinator,
    private val editProfile: EditProfile,
    private val fetchCurrentUser: FetchCurrentUser,
) : EditProfilePresenter() {

    override var viewHolder: ViewHolder<EditProfileView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var state: State = State.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    override fun onAppear() {
        super.onAppear()
        state = State.Loading
        getCurrentUser()
    }

    private var updateState: UpdateState = UpdateState.Idle
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var formErrors: FormErrors = FormErrors.empty()
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private fun getCurrentUser() {
        fetchCurrentUser { response ->
            state = when (response) {
                is FetchCurrentUser.Response.Success -> {
                    State.Content(user = response.user)
                }
                is FetchCurrentUser.Response.Error -> {
                    State.Error(title = response.title, message = response.message)
                }
            }
        }
    }

    override fun onNavigateBack() {
        delegate.onNavigateBack()
    }

    override fun onUpdateProfile(firstName: String, lastName: String, organizationName: String) {
        val currentState = state as? State.Content ?: return

        formErrors = FormErrors.empty()

        val fieldFirstName = firstName.trim()
        val fieldLastName = lastName.trim()
        val fieldOrganizationName = organizationName.trim()

        if (fieldFirstName.isBlank()) {
            formErrors = formErrors.copy(firstName = "First name must not be empty!")
        }

        if (fieldLastName.isBlank()) {
            formErrors = formErrors.copy(lastName = "Last name must not be empty!")
        }

        if (formErrors.isValid()) {
            updateState = UpdateState.Loading

            editProfile(
                firstName = fieldFirstName.takeIf {
                    it != currentState.user.firstName
                },
                lastName = fieldLastName.takeIf {
                    it != currentState.user.lastName
                },
                organizationName = fieldOrganizationName.takeIf {
                    it != currentState.user.organizationName
                }
            ) { response ->
                updateState = UpdateState.Idle
                when (response) {
                    is EditProfile.Response.Success -> {
                        alert.show(Alert {
                            title = "Profile successfully updated!"
                            buttons = listOf(Alert.Button {
                                title = "Ok"
                                event = Alert.Button.Event.DESTRUCTIVE
                                onClick = {}
                            })
                        })
                    }
                    is EditProfile.Response.InputErrors -> {
                        response.data.forEach { error ->
                            when (error.input) {
                                "entity" -> {
                                    alert.show(Alert {
                                        title = "Failed!"
                                        message = error.message
                                        buttons = listOf(Alert.Button {
                                            title = "Ok"
                                            event = Alert.Button.Event.DESTRUCTIVE
                                            onClick = {}
                                        })
                                    })
                                }
                                "first_name" -> {
                                    formErrors = formErrors.copy(firstName = error.message)
                                }
                                "last_name" -> {
                                    formErrors = formErrors.copy(lastName = error.message)
                                }
                            }
                        }
                    }
                    is EditProfile.Response.Error -> {
                        alert.show(Alert {
                            title = "Failed!"
                            message = response.message
                            buttons = listOf(Alert.Button {
                                title = "Ok"
                                event = Alert.Button.Event.DESTRUCTIVE
                                onClick = {}
                            })
                        })
                    }
                }

            }
        }
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }
}
