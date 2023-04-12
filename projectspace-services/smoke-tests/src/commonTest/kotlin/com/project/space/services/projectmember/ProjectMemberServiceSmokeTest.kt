package com.project.space.services.projectmember

import com.libraries.test.runTest
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class ProjectMemberServiceSmokeTest {
    @Test
    fun `test getProjectMembers`() = runTest {
        val service = makeSUT()

        val response = service.getProjectMembers(projectId = 6)

        println(response.toString())
    }

    @Test
    fun `test updateProjectMember`() = runTest {
        val service = makeSUT()

        val response = service.updateProjectMember(memberId = 11, memberLevelId = 2)

        println(response.toString())
    }

    @Test
    fun `test deleteProjectMember`() = runTest {
        val service = makeSUT()

        val response = service.deleteProjectMember(projectId = 6, memberId = 11)

        println(response.toString())
    }

    // region HELPERS
    private fun makeSUT(): ProjectMemberService =
        ProjectMemberService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
