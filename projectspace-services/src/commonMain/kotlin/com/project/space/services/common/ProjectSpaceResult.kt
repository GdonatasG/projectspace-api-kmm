package com.project.space.services.common

import com.project.space.services.common.response.ProjectSpaceErrorResponse

sealed class ProjectSpaceResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ProjectSpaceResult<T>()
    data class Exception(val exception: Throwable) : ProjectSpaceResult<Nothing>()
    object InsufficientPermissions : ProjectSpaceResult<Nothing>()
    object Unauthorized : ProjectSpaceResult<Nothing>()
    data class Error(val error: ProjectSpaceErrorResponse) : ProjectSpaceResult<Nothing>()

    inline fun onSuccess(continueWith: (T) -> Unit = {}): ProjectSpaceResult<T> {
        if (this is Success) continueWith(data)

        return this
    }

    inline fun onException(continueWith: (Throwable) -> Unit = {}): ProjectSpaceResult<T> {
        if (this is Exception) continueWith(exception)

        return this
    }

    inline fun onInsufficientPermissions(continueWith: () -> Unit = {}): ProjectSpaceResult<T> {
        if (this is InsufficientPermissions) continueWith()

        return this
    }

    inline fun onError(continueWith: (ProjectSpaceErrorResponse) -> Unit = {}): ProjectSpaceResult<T> {
        if (this is Error) continueWith(error)

        return this
    }
}
