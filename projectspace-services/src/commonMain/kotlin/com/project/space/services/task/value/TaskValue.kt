package com.project.space.services.task.value

import com.project.space.services.common.value.BodyValue
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

interface TaskValue : BodyValue {
    data class Title(private val value: String) : TaskValue {
        override val key: String = "title"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class Description(private val value: String) : TaskValue {
        override val key: String = "description"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class PriorityId(private val value: Int) : TaskValue {
        override val key: String = "priority_id"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class Assignees(private val value: Set<Int>) : TaskValue {
        override val key: String = "assignees"
        override val jsonElement: JsonElement = JsonArray(value.map { JsonPrimitive(it) })
    }
}
