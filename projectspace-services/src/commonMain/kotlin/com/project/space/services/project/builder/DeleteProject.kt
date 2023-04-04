package com.project.space.services.project.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues
import com.project.space.services.project.value.ProjectValue

internal class DeleteProject(id: Int): ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    init {
        bodyValues.put(ProjectValue.Id(id))
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.DELETE,
        endpoint = Config.API_ENDPOINT + "/project",
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
           "Content-Type" to "application/json"
        )
    )
}
