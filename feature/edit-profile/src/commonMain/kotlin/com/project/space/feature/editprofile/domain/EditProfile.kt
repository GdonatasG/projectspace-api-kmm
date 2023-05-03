package com.project.space.feature.editprofile.domain

interface EditProfile {
    sealed class Response {
        object Success : Response()
        data class Error(val title: String, val message: String) : Response()
        data class InputErrors(val data: List<InputError>) : Response()
    }

    data class InputError(val input: String, val message: String)

    operator fun invoke(
        firstName: String?,
        lastName: String?,
        organizationName: String?,
        completion: (Response) -> Unit
    )
}
