package com.project.space.services.common.value

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

interface BodyValue : ValuePrimitive {
    val jsonElement: JsonElement
}

open class BodyValues {
    private var values: MutableMap<String, JsonElement> = mutableMapOf()

    internal fun put(vararg bodyValues: BodyValue) {
        bodyValues.forEach { value ->
            values[value.key] = value.jsonElement
        }
    }


    /** Joins other [BodyValues] by overriding conflicting values **/
    internal open fun join(bodyValues: BodyValues) {
        if (bodyValues is BodyValuesPrebuilt) {
            bodyValues.build()
        }

        bodyValues.values.forEach { value ->
            values[value.key] = value.value
        }
    }

    internal open fun encodeToJsonObject(): JsonObject = JsonObject(values)

    internal open fun encodeToJsonString(): String = encodeToJsonObject().toString()
}

abstract class BodyValuesPrebuilt : BodyValues() {
    override fun encodeToJsonObject(): JsonObject {
        build()
        return super.encodeToJsonObject()
    }

    internal abstract fun build()
}
