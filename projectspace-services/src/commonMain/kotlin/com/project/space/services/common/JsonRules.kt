package com.project.space.services.common

import com.project.space.services.common.exception.DecodeFromStringException
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
    prettyPrint = true
    encodeDefaults = true
    useAlternativeNames = true
}

@OptIn(InternalSerializationApi::class)
fun <T : Any> decodeFromString(string: String, clazz: KClass<T>): T {
    return try {
        json.decodeFromString(clazz.serializer(), string)
    } catch (exception: SerializationException) {
        throw DecodeFromStringException(exception.message ?: "Failed to decode ${clazz.simpleName} from String")
    }
}
