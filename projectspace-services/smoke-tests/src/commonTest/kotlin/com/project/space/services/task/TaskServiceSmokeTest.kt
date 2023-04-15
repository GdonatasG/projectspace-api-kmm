package com.project.space.services.task

import com.libraries.test.runTest
import com.libraries.utils.DateTimeFormatter
import com.project.space.services.common.Timestamp
import com.project.space.services.utils.createAuthorizedProjectSpaceHttpClient
import kotlin.test.Test

class TaskServiceSmokeTest {
    @Test
    fun `test getTask`() = runTest {
        val service = makeSUT()

        val response = service.getTask(id = 20)

        println(response.toString())
    }

    @Test
    fun `test getSessionUserAssignedTasks`() = runTest {
        val service = makeSUT()

        val response = service.getSessionUserAssignedTasks(projectId = 6)

        println(response.toString())
    }

    @Test
    fun `test getProjectTasks`() = runTest {
        val service = makeSUT()

        val response = service.getProjectTasks(projectId = 5)

        println(response.toString())
    }

    @Test
    fun `test createTask`() = runTest {
        val service = makeSUT()

        val response = service.createTask(projectId = 6, title = "NewTask4", priorityId = 1) {
            description("NewTaskDescription4")
            startDate(Timestamp(1681552412))
            endDate(Timestamp(1681552412))
            assignees(setOf(4))
        }

        println(response.toString())
    }

    @Test
    fun `test closeTask`() = runTest {
        val service = makeSUT()

        val response = service.closeTask(id = 20)

        println(response.toString())
    }

    @Test
    fun `test openTask`() = runTest {
        val service = makeSUT()

        val response = service.openTask(id = 20)

        println(response.toString())
    }

    // region HELPERS
    private fun makeSUT(): TaskService =
        TaskService(client = createAuthorizedProjectSpaceHttpClient())
    // endregion
}
