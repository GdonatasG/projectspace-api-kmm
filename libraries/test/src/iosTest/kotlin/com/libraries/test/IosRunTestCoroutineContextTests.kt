package com.libraries.test

import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertTrue

class IosRunTestCoroutineContextTests {

    @Test
    fun test_runTestCoroutineContext_works() = runTest {
        assertTrue { getTrue() }
    }

    private suspend fun getTrue(): Boolean {
        delay(1)
        return true
    }
}
