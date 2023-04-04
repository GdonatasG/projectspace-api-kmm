package com.project.space.services.project

import com.libraries.test.runTest
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class ProjectServiceSmokeTest {
    @Test
    fun `test getProject`() = runTest {
        val service = makeSUT()

        val response = service.getProject(id = 6)

        println(response)
    }

    @Test
    fun `test getProjectStatistics`() = runTest {
        val service = makeSUT()

        val response = service.getProjectStatistics(id = 6)

        println(response)
    }

    @Test
    fun `test getProjectMonthlyRisk`() = runTest {
        val service = makeSUT()

        val response = service.getProjectMonthlyRisk(id = 6)

        println(response)
    }

    @Test
    fun `test getSessionUserAvailableProjects`() = runTest {
        val service = makeSUT()

        val response = service.getSessionUserAvailableProjects {
            owned(true)
        }

        println(response)
    }

    @Test
    fun `test createProject`() = runTest {
        val service = makeSUT()

        val response = service.createProject(name = "123test") {
            description("123test description")
        }

        println(response)
    }

    // region HELPERS
    private fun makeSUT(): ProjectService = ProjectService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
