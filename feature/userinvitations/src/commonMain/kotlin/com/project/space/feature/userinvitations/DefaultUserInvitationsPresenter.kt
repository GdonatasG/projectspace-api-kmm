package com.project.space.feature.userinvitations

import com.libraries.alerts.Alert
import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.userinvitations.domain.AcceptInvitation
import com.project.space.feature.userinvitations.domain.GetInvitations
import com.project.space.feature.userinvitations.domain.Invitation
import com.project.space.feature.userinvitations.domain.UserInvitationsDelegate

class DefaultUserInvitationsPresenter(
    private val scope: PlatformScopeManager,
    private val alert: Alert.Coordinator,
    private val getInvitations: GetInvitations,
    private val acceptInvitation: AcceptInvitation,
    private val delegate: UserInvitationsDelegate
) : UserInvitationsPresenter() {
    override var viewHolder: ViewHolder<UserInvitationsView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var state: State = State.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var acceptState: AcceptState = AcceptState.Idle
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    override fun onAppear() {
        super.onAppear()

        state = State.Loading
        fetchInvitations()
    }

    private fun fetchInvitations() {
        getInvitations { response ->
            when (response) {
                is GetInvitations.Response.Success -> {
                    val invitations = response.data

                    if (invitations.isEmpty()) {
                        state = State.Empty(
                            title = "No invitations found",
                            message = "Feel free to refresh the list by tapping button below."
                        )

                        return@getInvitations
                    }

                    state = State.Content(data = invitations)
                }
                is GetInvitations.Response.Error -> {
                    state = State.Error(title = "Unable to get invitations!", message = response.message)
                }
            }

        }
    }

    override fun onRefresh() {
        state = State.Refreshing
        scope.cancelAllJobs()
        fetchInvitations()
    }

    override fun onRetry() {
        state = State.Refreshing
        scope.cancelAllJobs()
        fetchInvitations()
    }

    override fun onNavigateBack() {
        delegate.onNavigateBack()
    }

    override fun onAccept(invitation: Invitation) {
        alert.show(Alert {
            title = "Accept invitation to ${invitation.projectName}?"
            buttons = listOf(Alert.Button.Cancel(), Alert.Button {
                title = "Accept"
                event = Alert.Button.Event.DESTRUCTIVE
                onClick = {
                    _acceptInvitation(invitation)
                }
            })
        })
    }

    private fun _acceptInvitation(invitation: Invitation) {
        acceptState = AcceptState.Loading
        acceptInvitation(invitation) { response ->
            acceptState = AcceptState.Idle
            fetchInvitations()
            when (response) {
                is AcceptInvitation.Response.Success -> {
                    alert.show(Alert {
                        title = "Invitation to ${invitation.projectName} successfully accepted!"
                        buttons = listOf(Alert.Button {
                            title = "OK"
                            event = Alert.Button.Event.DESTRUCTIVE
                        })
                    })
                }
                is AcceptInvitation.Response.Error -> {
                    alert.show(Alert {
                        title = "Unable to accept invitation to ${invitation.projectName}!"
                        message = response.message
                        buttons = listOf(Alert.Button {
                            title = "OK"
                            event = Alert.Button.Event.DESTRUCTIVE
                        })
                    })
                }
            }
        }
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }


}
