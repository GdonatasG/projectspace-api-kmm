package com.project.space.feature.userinvitations.domain

interface GetInvitations {
    sealed class Response {
        data class Success(val data: List<Invitation>) : Response()
        data class Error(val message: String) : Response()
    }

    operator fun invoke(completion: (Response) -> Unit)
}
