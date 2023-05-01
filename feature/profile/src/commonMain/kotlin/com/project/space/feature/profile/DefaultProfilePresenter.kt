package com.project.space.feature.profile

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.profile.domain.GetCurrentUser
import com.project.space.feature.profile.domain.GetInvitationsCount
import kotlinx.coroutines.delay

class DefaultProfilePresenter(
    private val scope: PlatformScopeManager,
    private val alert: Alert.Coordinator,
    private val getCurrentUser: GetCurrentUser,
    private val getInvitationsCount: GetInvitationsCount,
    private val delegate: ProfileDelegate
) : ProfilePresenter() {
    override var viewHolder: ViewHolder<ProfileView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var generalState: GeneralState = GeneralState.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var invitationsCountState: InvitationsCountState = InvitationsCountState.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    override fun onAppear() {
        super.onAppear()

        generalState = GeneralState.Loading
        _getCurrentUser()

        invitationsCountState = InvitationsCountState.Loading
    }

    override fun onResume() {
        super.onResume()
        _getInvitationCount()
    }

    private fun _getCurrentUser() {
        generalState = GeneralState.Content(user = getCurrentUser())
    }

    private fun _getInvitationCount() {
        getInvitationsCount { response ->
            invitationsCountState = when (response) {
                is GetInvitationsCount.Response.Success -> {
                    InvitationsCountState.Content(count = response.count)
                }
                is GetInvitationsCount.Response.Error -> {
                    InvitationsCountState.Error(
                        title = "Unable to get invitations count!",
                        message = response.message
                    )
                }
            }
        }
    }

    override fun onNavigateToEditProfile() {
        delegate.onNavigateToEditProfile()
    }

    override fun onNavigateToInvitations() {
        delegate.onNavigateToInvitations()
    }

    override fun onLogout() {
        alert.show(Alert {
            title = "Are you sure to logout?"
            buttons = listOf(Alert.Button.Cancel(), Alert.Button {
                title = "Logout"
                event = Alert.Button.Event.DESTRUCTIVE
                onClick = {
                    delegate.onLogout()
                }
            })
        })
    }

    override fun onRefresh() {
        generalState = GeneralState.Refreshing
        _getCurrentUser()

        invitationsCountState = InvitationsCountState.Refreshing
        _getInvitationCount()
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }

}
