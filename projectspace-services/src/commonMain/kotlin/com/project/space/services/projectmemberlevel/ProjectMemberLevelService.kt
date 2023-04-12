package com.project.space.services.projectmemberlevel

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.projectmemberlevel.builder.GetProjectMemberLevels
import com.project.space.services.projectmemberlevel.response.ProjectMemberLevelsDataListResponse

class ProjectMemberLevelService(private val client: ProjectSpaceHttpClient) {
    suspend fun getProjectMemberLevels(): ProjectSpaceResult<ProjectMemberLevelsDataListResponse> =
        client.request(GetProjectMemberLevels())
}
