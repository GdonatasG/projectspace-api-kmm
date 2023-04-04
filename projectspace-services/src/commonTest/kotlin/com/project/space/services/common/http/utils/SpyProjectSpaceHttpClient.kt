package com.project.space.services.common.http.utils

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.request.ProjectSpaceRequest
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

internal class SpyProjectSpaceHttpClient(
    private val response: ProjectSpaceResult<TestProjectSpaceResponse> = ProjectSpaceResult.Success(
        TestProjectSpaceResponse(success = true)
    )
) : ProjectSpaceHttpClient {
    private val _requests: MutableList<ProjectSpaceRequest> = mutableListOf()
    val requests: List<ProjectSpaceRequest>
        get() = _requests

    private val _responses: MutableList<ProjectSpaceResult<Any>> = mutableListOf()
    val responses: MutableList<ProjectSpaceResult<Any>>
        get() = _responses

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Any> request(request: ProjectSpaceRequest, clazz: KClass<T>): ProjectSpaceResult<T> {
        _requests.add(request)

        run {
            _responses.add(response)
            return response as ProjectSpaceResult<T>
        }
    }

}

@Serializable
internal class TestProjectSpaceResponse(
    val success: Boolean
)

internal class TestProjectSpaceRequest : ProjectSpaceRequest {
    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.GET,
        endpoint = "test"
    )
}
