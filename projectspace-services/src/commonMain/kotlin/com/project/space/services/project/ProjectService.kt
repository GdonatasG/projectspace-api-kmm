package com.project.space.services.project

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.project.builder.GetProject
import com.project.space.services.project.response.ProjectDataResponse

class ProjectService(private val client: ProjectSpaceHttpClient) {
    suspend fun getProject(id: Int): ProjectSpaceResult<ProjectDataResponse> = client.request(GetProject(id = id))
}
