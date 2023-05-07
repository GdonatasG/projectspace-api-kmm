package com.project.space.feature.dashboard.domain

import com.project.space.feature.dashboard.Statistics

interface GetStatistics {
    sealed class Response {
        data class Success(val data: Statistics) : Response()
        data class Error(val title: String, val message: String) : Response()
    }

    operator fun invoke(completion: (Response) -> Unit)
}
