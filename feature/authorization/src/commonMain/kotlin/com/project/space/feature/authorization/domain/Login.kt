package com.project.space.feature.authorization.domain

interface Login {
    sealed class Response {
        object Success : Response()
        data class Error(val message: String) : Response()
    }

    operator fun invoke(username: String, password: String, completion: (Response) -> Unit)
}
