package com.project.space.services.task.builder

import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.project.space.services.common.Config
import com.project.space.services.common.EndDate
import com.project.space.services.common.StartDate
import com.project.space.services.common.Timestamp
import com.project.space.services.common.request.ProjectSpaceRequest
import com.project.space.services.common.value.BodyValues
import com.project.space.services.task.value.TaskValue

class UpdateTask(private val id: Int) : ProjectSpaceRequest {
    private val bodyValues: BodyValues = BodyValues()

    override fun build(): HttpRequest = HttpRequest(
        method = HttpMethod.PUT,
        endpoint = Config.API_ENDPOINT + "/task/$id",
        body = bodyValues.encodeToJsonString(),
        headers = mapOf(
           "Content-Type" to "application/json"
        )
    )

    fun title(value: String): UpdateTask {
        bodyValues.put(TaskValue.Title(value))

        return this
    }

    fun description(value: String): UpdateTask {
        bodyValues.put(TaskValue.Description(value))

        return this
    }

    fun priorityId(value: Int): UpdateTask {
        bodyValues.put(TaskValue.PriorityId(value))

        return this
    }

    fun startDate(value: Timestamp): UpdateTask {
        bodyValues.put(StartDate(value))

        return this
    }

    fun endDate(value: Timestamp): UpdateTask {
        bodyValues.put(EndDate(value))

        return this
    }

    fun assignees(value: Set<Int>): UpdateTask {
        bodyValues.put(TaskValue.Assignees(value))

        return this
    }
}
