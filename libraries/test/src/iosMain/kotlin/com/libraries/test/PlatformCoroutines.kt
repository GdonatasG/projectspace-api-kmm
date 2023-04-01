package com.libraries.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

actual fun runTest(block: suspend CoroutineScope.() -> Unit) = runBlocking(newSingleThreadContext("testRunner")) {
    this.block()
}
