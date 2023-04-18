package com.libraries.preferences

import kotlinx.serialization.json.Json

internal object Serializer {
    val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        useAlternativeNames = false
    }
}


