package com.project.space.feature.dashboard.domain

import com.project.space.feature.dashboard.Member

interface GetMembers {
    sealed class Response {
        data class Success(val data: List<Member>) : Response()
        data class Error(val title: String, val message: String) : Response()
    }

    operator fun invoke(completion: (Response) -> Unit)
}
