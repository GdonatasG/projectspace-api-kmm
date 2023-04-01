package com.libraries.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

actual fun runTest(block: suspend CoroutineScope.() -> Unit) =
    runBlocking(Executors.newSingleThreadExecutor().asCoroutineDispatcher()) { this.block() }
