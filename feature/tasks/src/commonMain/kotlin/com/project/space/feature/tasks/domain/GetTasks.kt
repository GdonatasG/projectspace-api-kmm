package com.project.space.feature.tasks.domain

import com.project.space.feature.tasks.Tab

interface GetTasks {
    sealed class Response {
        data class Success(val data: List<Task>) : Response()
        data class Error(val message: String) : Response()
    }

    operator fun invoke(tab: Tab, completion: (Response) -> Unit)
}
