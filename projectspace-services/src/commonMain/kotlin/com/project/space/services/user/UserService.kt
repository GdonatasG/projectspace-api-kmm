package com.project.space.services.user

import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.common.http.ProjectSpaceHttpClient
import com.project.space.services.common.http.request
import com.project.space.services.common.response.SimpleSuccessResponse
import com.project.space.services.user.builder.UpdateSessionUser

class UserService(private val client: ProjectSpaceHttpClient) {
    suspend fun updateSessionUser(builder: UpdateSessionUser.() -> Unit): ProjectSpaceResult<SimpleSuccessResponse> =
        client.request(UpdateSessionUser().apply(builder))
}
