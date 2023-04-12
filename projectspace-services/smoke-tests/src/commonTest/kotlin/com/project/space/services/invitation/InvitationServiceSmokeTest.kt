package com.project.space.services.invitation

import com.libraries.test.runTest
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class InvitationServiceSmokeTest {
    @Test
    fun `test invite`() = runTest {
        val service = makeSUT()

        val response = service.invite(email = "TestEmail@email.com", projectId = 6)

        println(response)
    }

    // region HELPERS
    private fun makeSUT(): InvitationService = InvitationService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
