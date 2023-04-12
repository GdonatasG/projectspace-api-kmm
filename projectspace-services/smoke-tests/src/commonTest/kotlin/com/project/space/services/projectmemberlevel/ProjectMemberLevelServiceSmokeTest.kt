package com.project.space.services.projectmemberlevel

import com.libraries.test.runTest
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class ProjectMemberLevelServiceSmokeTest {
    @Test
    fun `test getProjectMemberLevels`() = runTest {
        val service = makeSUT()

        val response = service.getProjectMemberLevels()

        println(response.toString())
    }

    // region HELPERS
    private fun makeSUT(): ProjectMemberLevelService =
        ProjectMemberLevelService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
