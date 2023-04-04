package com.project.space.services.user

import com.libraries.test.runTest
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class UserServiceSmokeTest {
    @Test
    fun `test updateSessionUser`() = runTest {
        val service: UserService = makeSUT()

        val response = service.updateSessionUser {
            firstName("testfirstuser123")
            lastName("testlastuser123")
            organizationName("organization123")
        }

        println(response)
    }


    // region HELPERS
    private fun makeSUT(): UserService = UserService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
