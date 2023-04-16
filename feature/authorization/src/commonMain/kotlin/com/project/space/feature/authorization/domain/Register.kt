package com.project.space.feature.authorization.domain

interface Register {
    sealed class Response {
        object Success : Response()
        data class Error(val message: String) : Response()
        data class InputErrors(val data: List<Register.InputError>) : Response()
    }

    data class InputError(val input: String, val message: String)

    operator fun invoke(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        completion: (Response) -> Unit
    )
}
