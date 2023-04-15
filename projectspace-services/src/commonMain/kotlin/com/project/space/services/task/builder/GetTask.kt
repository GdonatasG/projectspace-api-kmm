package com.project.space.services.task.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest

internal class GetTask(private val id: Int) : ProjectSpaceRequest {
    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.GET,
        endpoint = Config.API_ENDPOINT + "/task/$id"
    )
}
