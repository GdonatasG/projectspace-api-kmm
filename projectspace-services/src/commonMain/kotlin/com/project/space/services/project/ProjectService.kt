package com.project.space.services.project

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.project.builder.GetProject
import com.project.space.services.project.builder.GetProjectRisk
import com.project.space.services.project.builder.GetProjectStatistics
import com.project.space.services.project.response.ProjectDataResponse
import com.project.space.services.project.response.ProjectRiskDataResponse
import com.project.space.services.project.response.ProjectStatisticsDataResponse

class ProjectService(private val client: ProjectSpaceHttpClient) {
    suspend fun getProject(id: Int): ProjectSpaceResult<ProjectDataResponse> = client.request(GetProject(id = id))

    suspend fun getProjectStatistics(id: Int): ProjectSpaceResult<ProjectStatisticsDataResponse> =
        client.request(GetProjectStatistics(id = id))

    suspend fun getProjectRisk(id: Int): ProjectSpaceResult<ProjectRiskDataResponse> =
        client.request(GetProjectRisk(id = id))
}
