package com.libraries.http.utils

import kotlinx.serialization.json.JsonBuilder

fun JsonBuilder.applyRequired() {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
    coerceInputValues = true
}
