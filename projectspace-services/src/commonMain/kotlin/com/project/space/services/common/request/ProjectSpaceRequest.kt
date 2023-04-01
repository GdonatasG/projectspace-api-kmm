package com.project.space.services.common.request

import com.libraries.http.models.HttpRequest

interface ProjectSpaceRequest {
    fun build(): HttpRequest
}
