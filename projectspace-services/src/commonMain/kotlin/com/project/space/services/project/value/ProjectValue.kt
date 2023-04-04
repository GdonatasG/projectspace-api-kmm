package com.project.space.services.project.value

import com.project.space.services.common.value.BodyValue
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

sealed interface ProjectValue : BodyValue {
    data class Name(private val value: String) : ProjectValue {
        override val key: String = "name"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class Description(private val value: String) : ProjectValue {
        override val key: String = "description"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }
}
