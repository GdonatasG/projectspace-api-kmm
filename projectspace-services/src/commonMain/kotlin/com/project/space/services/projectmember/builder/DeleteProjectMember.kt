package com.project.space.services.projectmember.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues
import com.project.space.services.project.value.ProjectValue
import com.project.space.services.projectmember.value.ProjectMemberValue

internal class DeleteProjectMember(projectId: Int, memberId: Int) : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    init {
        bodyValues.put(ProjectValue.Id(projectId), ProjectMemberValue.MemberId(memberId))
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.DELETE,
        endpoint = Config.API_ENDPOINT + "/project/members",
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
           "Content-Type" to "application/json"
        )
    )
}
