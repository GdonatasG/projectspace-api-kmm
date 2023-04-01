package com.libraries.test

import platform.Foundation.NSUUID

actual fun generateUUID(): String = NSUUID().UUIDString()
