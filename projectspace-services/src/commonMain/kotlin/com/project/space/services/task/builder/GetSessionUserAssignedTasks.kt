package com.project.space.services.task.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.libraries.http.models.QueryParams
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest

internal class GetSessionUserAssignedTasks(projectId: Int) : ProjectSpaceRequest {
    private val params: QueryParams = QueryParams()

    init {
        params.put("project_id", projectId)
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.GET, endpoint = Config.API_ENDPOINT + "/task/assigned", params = params
    )
}
