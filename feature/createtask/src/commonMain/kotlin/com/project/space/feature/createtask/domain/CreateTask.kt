package com.project.space.feature.createtask.domain

interface CreateTask {
    sealed class Response {
        object Success : Response()
        data class Error(val message: String) : Response()
        data class InputErrors(val data: List<InputError>) : Response()
    }

    data class InputError(val input: String, val message: String)

    operator fun invoke(
        title: String,
        priorityId: Int,
        description: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        assignees: List<Int> = emptyList(),
        completion: (Response) -> Unit
    )
}
