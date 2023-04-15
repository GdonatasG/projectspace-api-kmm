package com.project.space.services.common

import com.libraries.utils.DateTimeFormatter
import com.project.space.services.common.value.BodyValue
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

data class Timestamp(val value: Long) {
    init {
        require(value > 0)
    }
}

abstract class DateValue(override val key: String, timestamp: Timestamp) : BodyValue {
    private val pattern: String = "yyyy-MM-dd'T'HH:mm:ss"
    private val formatter = DateTimeFormatter.shared

    override val jsonElement: JsonElement = JsonPrimitive(formatter.stringUTC0(timestamp.value.toDouble(), pattern))
}

data class StartDate(private val timestamp: Timestamp) : DateValue(key = "start_date", timestamp = timestamp)
data class EndDate(private val timestamp: Timestamp) : DateValue(key = "end_date", timestamp = timestamp)
