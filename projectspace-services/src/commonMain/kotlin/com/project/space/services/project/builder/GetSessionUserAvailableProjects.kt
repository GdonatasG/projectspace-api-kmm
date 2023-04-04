package com.project.space.services.project.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.libraries.http.models.QueryParams
import com.project.space.services.common.Config
import com.project.space.services.common.request.ProjectSpaceRequest

class GetSessionUserAvailableProjects : ProjectSpaceRequest {
    private val params: QueryParams = QueryParams()
    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.GET,
        endpoint = Config.API_ENDPOINT + "/project/available",
        params = params
    )

    fun owned(value: Boolean): GetSessionUserAvailableProjects {
        params.put("owned", "$value")

        return this
    }
}
