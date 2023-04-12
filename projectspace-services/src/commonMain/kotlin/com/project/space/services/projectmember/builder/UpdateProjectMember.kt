package com.project.space.services.projectmember.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues
import com.project.space.services.projectmember.value.ProjectMemberValue

internal class UpdateProjectMember(memberId: Int, memberLevelId: Int) : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    init {
        bodyValues.put(ProjectMemberValue.MemberId(memberId), ProjectMemberValue.MemberLevelId(memberLevelId))
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.PUT,
        endpoint = Config.API_ENDPOINT + "/project/members/update",
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
           "Content-Type" to "application/json"
        )
    )
}
