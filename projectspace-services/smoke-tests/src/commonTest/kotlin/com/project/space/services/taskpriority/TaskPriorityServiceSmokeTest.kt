package com.project.space.services.taskpriority

import com.libraries.test.runTest
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class TaskPriorityServiceSmokeTest {
    @Test
    fun `test getTaskPriorities`() = runTest {
        val service = makeSUT()

        val response = service.getTaskPriorities()

        println(response.toString())
    }

    // region HELPERS
    private fun makeSUT(): TaskPriorityService =
        TaskPriorityService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
