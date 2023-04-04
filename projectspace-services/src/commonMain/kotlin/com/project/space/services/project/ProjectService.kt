package com.project.space.services.project

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.project.builder.GetProject
import com.project.space.services.project.builder.GetProjectMonthlyRisk
import com.project.space.services.project.builder.GetProjectStatistics
import com.project.space.services.project.builder.GetSessionUserAvailableProjects
import com.project.space.services.project.response.ProjectDataResponse
import com.project.space.services.project.response.ProjectRiskDataResponse
import com.project.space.services.project.response.ProjectStatisticsDataResponse
import com.project.space.services.project.response.ProjectsDataListResponse

class ProjectService(private val client: ProjectSpaceHttpClient) {
    suspend fun getProject(id: Int): ProjectSpaceResult<ProjectDataResponse> = client.request(GetProject(id = id))

    suspend fun getProjectStatistics(id: Int): ProjectSpaceResult<ProjectStatisticsDataResponse> =
        client.request(GetProjectStatistics(id = id))

    suspend fun getProjectMonthlyRisk(id: Int): ProjectSpaceResult<ProjectRiskDataResponse> =
        client.request(GetProjectMonthlyRisk(id = id))

    suspend fun getSessionUserAvailableProjects(): ProjectSpaceResult<ProjectsDataListResponse> =
        client.request(GetSessionUserAvailableProjects())
}
