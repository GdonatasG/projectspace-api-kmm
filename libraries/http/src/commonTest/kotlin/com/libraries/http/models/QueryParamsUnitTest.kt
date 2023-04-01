package com.libraries.http.models

import kotlin.test.Test
import kotlin.test.assertEquals

class QueryParamsUnitTest {
    @Test
    fun test_put_addsIntParamAsString() {
        val params = QueryParams()
        params.put("key", 1)

        assertEquals("1", params.parameters["key"])
    }

    @Test
    fun test_put_ignoresNullIntValue() {
        val params = QueryParams()
        val nullInt: Int? = null
        params.put("key", nullInt)

        assertEquals(0, params.parameters.size)
    }

    @Test
    fun test_put_addsStringParam() {
        val params = QueryParams()
        params.put("key", "value")

        assertEquals("value", params.parameters["key"])
    }

    @Test
    fun test_put_ignoresNullStringValue() {
        val params = QueryParams()
        val nullString: String? = null
        params.put("key", nullString)

        assertEquals(0, params.parameters.size)
    }

    @Test
    fun test_put_joinsValueListOfTypeIntIntoSeparatedString() {
        val params = QueryParams()
        params.put("key", listOf(10, 15, 12))

        assertEquals("10,15,12", params.parameters["key"])
    }

    @Test
    fun test_put_ignoresEmptyValueListOfTypeInt() {
        val params = QueryParams()
        params.put("key", listOf<Int>())

        assertEquals(0, params.parameters.size)
    }

    @Test
    fun test_put_joinsValueListOfTypeStringIntoSeparatedString() {
        val params = QueryParams()
        params.put("key", listOf("10", "15", "12"))

        assertEquals("10,15,12", params.parameters["key"])
    }

    @Test
    fun test_put_ignoresEmptyValueListOfTypeString() {
        val params = QueryParams()
        params.put("key", listOf<String>())

        assertEquals(0, params.parameters.size)
    }
}
