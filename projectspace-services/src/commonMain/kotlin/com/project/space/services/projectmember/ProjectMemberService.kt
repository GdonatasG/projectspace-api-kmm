package com.project.space.services.projectmember

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.projectmember.builder.GetProjectMembers
import com.project.space.services.projectmember.response.ProjectMembersDataListResponse

class ProjectMemberService(private val client: ProjectSpaceHttpClient) {
    suspend fun getProjectMembers(projectId: Int): ProjectSpaceResult<ProjectMembersDataListResponse> =
        client.request(GetProjectMembers(projectId = projectId))
}
