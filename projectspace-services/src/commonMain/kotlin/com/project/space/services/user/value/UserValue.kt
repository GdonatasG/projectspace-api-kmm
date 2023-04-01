package com.project.space.services.user.value

import com.project.space.services.common.value.BodyValue
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

internal sealed interface UserValue : BodyValue {
    data class Username(private val value: String) : UserValue {
        override val key: String = "username"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class FirstName(private val value: String) : UserValue {
        override val key: String = "first_name"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class LastName(private val value: String) : UserValue {
        override val key: String = "last_name"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class Email(private val value: String) : UserValue {
        override val key: String = "email"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class Password(private val value: String) : UserValue {
        override val key: String = "password"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class Role(private val value: String) : UserValue {
        override val key: String = "role"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class OrganizationName(private val value: String) : UserValue {
        override val key: String = "organization_name"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

}
