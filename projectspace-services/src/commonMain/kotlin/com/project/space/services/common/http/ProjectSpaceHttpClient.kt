package com.project.space.services.common.http

import com.libraries.http.HttpClient
import com.libraries.http.exceptions.ConnectTimeoutException
import com.libraries.http.exceptions.UnresolvedAddressException
import com.libraries.http.exceptions.UrlEncodingException
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.decodeFromString
import com.project.space.services.common.exception.DecodeFromStringException
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.response.ProjectSpaceErrorResponse
import kotlin.reflect.KClass

interface ProjectSpaceHttpClient {
    suspend fun <T : Any> request(request: ProjectSpaceRequest, clazz: KClass<T>): ProjectSpaceResult<T>
}

class DefaultProjectSpaceHttpClient(private val client: HttpClient) : ProjectSpaceHttpClient {
    override suspend fun <T : Any> request(request: ProjectSpaceRequest, clazz: KClass<T>): ProjectSpaceResult<T> {
        try {
            val response = client.request(request.build())

            if (response.isSuccessful) {
                return ProjectSpaceResult.Success(decodeFromString(response.body, clazz))
            }

            if (response.code == 401) {
                return ProjectSpaceResult.Unauthorized
            }

            if (response.code == 403) {
                return ProjectSpaceResult.Unauthorized
            }

            val errorResponse = decodeFromString(response.body, ProjectSpaceErrorResponse::class)

            return ProjectSpaceResult.Error(errorResponse)
        } catch (exception: UrlEncodingException) {
            return ProjectSpaceResult.Exception(exception)
        } catch (exception: UnresolvedAddressException) {
            return ProjectSpaceResult.Exception(exception)
        } catch (exception: ConnectTimeoutException) {
            return ProjectSpaceResult.Exception(exception)
        } catch (exception: DecodeFromStringException) {
            return ProjectSpaceResult.Exception(exception)
        }
    }
}

suspend inline fun <reified T : Any> ProjectSpaceHttpClient.request(request: ProjectSpaceRequest): ProjectSpaceResult<T> {
    return this.request(request, T::class)
}
