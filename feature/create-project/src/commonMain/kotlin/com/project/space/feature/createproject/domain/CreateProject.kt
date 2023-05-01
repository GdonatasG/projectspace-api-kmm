package com.project.space.feature.createproject.domain

interface CreateProject {
    sealed class Response {
        object Success : Response()
        data class Error(val message: String) : Response()
        data class InputErrors(val data: List<InputError>) : Response()
    }

    data class InputError(val input: String, val message: String)

    operator fun invoke(name: String, description: String, completion: (Response) -> Unit)
}
