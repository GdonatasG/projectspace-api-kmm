package com.project.space.services.taskstatus

import com.libraries.test.runTest
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class TaskStatusServiceSmokeTest {
    @Test
    fun `test getTaskStatuses`() = runTest {
        val service = makeSUT()

        val response = service.getTaskStatuses()

        println(response.toString())
    }

    // region HELPERS
    private fun makeSUT(): TaskStatusService =
        TaskStatusService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
