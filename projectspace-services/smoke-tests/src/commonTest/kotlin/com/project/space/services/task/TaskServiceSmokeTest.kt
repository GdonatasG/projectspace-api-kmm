package com.project.space.services.task

import com.libraries.test.runTest
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class TaskServiceSmokeTest {
    @Test
    fun `test getTask`() = runTest {
        val service = makeSUT()

        val response = service.getTask(id = 1)

        println(response.toString())
    }

    @Test
    fun `test getSessionUserAssignedTasks`() = runTest {
        val service = makeSUT()

        val response = service.getSessionUserAssignedTasks(projectId = 6)

        println(response.toString())
    }

    // region HELPERS
    private fun makeSUT(): TaskService =
        TaskService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
