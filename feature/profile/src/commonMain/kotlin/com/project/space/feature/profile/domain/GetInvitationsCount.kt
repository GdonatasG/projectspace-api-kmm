package com.project.space.feature.profile.domain

interface GetInvitationsCount {
    sealed class Response {
        data class Success(val count: Int): Response()
        data class Error(val message: String) : Response()
    }

    operator fun invoke(completion: (Response) -> Unit)
}
