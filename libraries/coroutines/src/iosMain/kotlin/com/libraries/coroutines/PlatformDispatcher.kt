package com.libraries.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val PlatformDispatcher: CoroutineDispatcher = Dispatchers.Main
