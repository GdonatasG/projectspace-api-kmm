package com.libraries.test

import java.util.UUID

actual fun generateUUID(): String = UUID.randomUUID().toString()
