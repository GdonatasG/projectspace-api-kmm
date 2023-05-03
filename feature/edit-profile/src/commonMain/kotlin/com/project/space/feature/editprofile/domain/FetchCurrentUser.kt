package com.project.space.feature.editprofile.domain

interface FetchCurrentUser {
    sealed class Response {
        data class Success(val user: CurrentUser) : Response()
        data class Error(val title: String, val message: String) : Response()
    }

    operator fun invoke(completion: (Response) -> Unit)
}

data class CurrentUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val organizationName: String,
)
