package com.libraries.preferences

import com.russhwolf.settings.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

internal class MultiplatformSettingsPreferences(private val engine: Settings) : Preferences {
    override fun contains(key: String): Boolean = engine.contains(key)

    override fun remove(key: String) = engine.minusAssign(key)

    @OptIn(InternalSerializationApi::class)
    @Throws(FailedToDecodeFromString::class)
    override fun <R : Any> getObject(key: String, clazz: KClass<R>): R? {
        if (contains(key).not()) return null

        val objectString = engine.get(key, defaultValue = "{}")

        return try {
            val serializer = clazz.serializer()
            Serializer.json.decodeFromString(serializer, objectString)
        } catch (error: SerializationException) {
            val message = error.message ?: "Failed to decode from String to ${clazz.simpleName}. String = $objectString"
            throw FailedToDecodeFromString(message = message)
        }
    }

    @OptIn(InternalSerializationApi::class)
    @Throws(FailedToEncodeToString::class)
    override fun <R : Any> setObject(key: String, value: R, clazz: KClass<R>) {
        try {
            val serializer = clazz.serializer()
            val objectString = Serializer.json.encodeToString(serializer, value)
            engine[key] = objectString

        } catch (error: SerializationException) {
            val message = error.message ?: "Failed to encode to String from ${clazz.simpleName}. From = $value"
            throw FailedToEncodeToString(message = message)
        }
    }
}
