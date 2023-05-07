package com.project.space.feature.inviteuser.domain

interface InviteUser {
    sealed class Response {
        object Success : Response()
        data class Error(val title: String, val message: String) : Response()
        data class InputErrors(val data: List<InputError>) : Response()
    }

    data class InputError(val input: String, val message: String)

    operator fun invoke(email: String, completion: (Response) -> Unit)
}
