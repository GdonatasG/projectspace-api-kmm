package com.project.space.feature.userinvitations.domain

interface AcceptInvitation {
    sealed class Response {
        object Success : Response()
        data class Error(val message: String) : Response()
    }

    operator fun invoke(invitation: Invitation, completion: (Response) -> Unit)
}
