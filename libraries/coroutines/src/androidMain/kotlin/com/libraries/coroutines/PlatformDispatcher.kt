package com.libraries.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher

actual val PlatformDispatcher: CoroutineDispatcher = Dispatchers.Default
