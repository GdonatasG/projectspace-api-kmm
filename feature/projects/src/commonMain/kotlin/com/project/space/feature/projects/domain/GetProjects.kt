package com.project.space.feature.projects.domain

import com.project.space.feature.projects.Tab

interface GetProjects {
    sealed class Response {
        data class Success(val data: List<Project>) : Response()
        data class Error(val message: String) : Response()
    }

    operator fun invoke(tab: Tab, completion: (Response) -> Unit)
}
