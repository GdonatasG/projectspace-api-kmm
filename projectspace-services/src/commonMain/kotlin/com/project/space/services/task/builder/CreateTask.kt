package com.project.space.services.task.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.EndDate
import com.project.space.services.common.StartDate
import com.project.space.services.common.Timestamp
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues
import com.project.space.services.project.value.ProjectValue
import com.project.space.services.task.value.TaskValue

class CreateTask(projectId: Int, title: String, priorityId: Int) : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    init {
        bodyValues.put(
            ProjectValue.Id(projectId),
            TaskValue.Title(title),
            TaskValue.PriorityId(priorityId)
        )
    }

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.POST,
        endpoint = Config.API_ENDPOINT + "/task",
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
            "Content-Type" to "application/json"
        )
    )

    fun description(value: String): CreateTask {
        bodyValues.put(TaskValue.Description(value))

        return this
    }

    fun startDate(value: Timestamp): CreateTask {
        bodyValues.put(StartDate(value))

        return this
    }

    fun endDate(value: Timestamp): CreateTask {
        bodyValues.put(EndDate(value))

        return this
    }

    fun assignees(value: Set<Int>): CreateTask {
        bodyValues.put(TaskValue.Assignees(value))

        return this
    }
}
