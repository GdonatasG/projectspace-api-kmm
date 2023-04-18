package com.libraries.preferences

import com.russhwolf.settings.Settings
import kotlin.reflect.KClass

interface Preferences {
    companion object {
        val shared by lazy { create() }
        fun create(): Preferences = MultiplatformSettingsPreferences(Settings())
    }

    @Throws(FailedToEncodeToString::class)
    fun <R : Any> setObject(key: String, value: R, clazz: KClass<R>)

    @Throws(FailedToDecodeFromString::class)
    fun <R : Any> getObject(key: String, clazz: KClass<R>): R?

    /** Returns true if there is a value stored at key, or false otherwise */
    operator fun contains(key: String): Boolean

    /** Removes the value stored at key **/
    fun remove(key: String)
}

class FailedToDecodeFromString(message: String) : Exception(message)
class FailedToEncodeToString(message: String) : Exception(message)

@Throws(FailedToDecodeFromString::class)
inline fun <reified T : Any> Preferences.getObject(key: String): T? = getObject(key, T::class)

@Throws(FailedToEncodeToString::class)
inline fun <reified T : Any> Preferences.setObject(key: String, value: T) = setObject(key, value, T::class)
