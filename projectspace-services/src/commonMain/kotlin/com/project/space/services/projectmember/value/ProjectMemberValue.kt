package com.project.space.services.projectmember.value

import com.project.space.services.common.value.BodyValue
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

sealed interface ProjectMemberValue : BodyValue {
    data class MemberId(private val value: Int) : ProjectMemberValue {
        override val key: String = "member_id"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }

    data class MemberLevelId(private val value: Int) : ProjectMemberValue {
        override val key: String = "member_level_id"
        override val jsonElement: JsonElement = JsonPrimitive(value)
    }
}
