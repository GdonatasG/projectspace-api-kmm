package com.libraries.http.ktor

import com.libraries.http.HttpClient
import com.libraries.http.exceptions.UrlEncodingException
import com.libraries.http.models.HttpMethod
import com.libraries.http.models.HttpRequest
import com.libraries.http.models.QueryParams
import com.libraries.http.models.toKtor
import com.libraries.test.runTest
import io.ktor.client.engine.mock.*
import io.ktor.content.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.*

class KtorHttpClientUnitTest {

    @Test
    fun test_request_addsHeaderParamsCorrectly() = runTest {
        val mockEngine = MockEngine {
            respond("any content")
        }
        val sut = makeSUT(mockEngine, testBaseUrl)

        val expectedHeaders = mapOf(
            "Authorization" to "Bearer token",
            "Accept" to "application/json"
        )

        sut.request(TestHttpRequest(headers = expectedHeaders))

        assertEquals(1, mockEngine.requestHistory.size)
        expectedHeaders.forEach { headerParam ->
            assertEquals(headerParam.value, mockEngine.requestHistory.first().headers[headerParam.key])

        }
    }

    @Test
    fun test_request_setsHttpMethodToGet() = runTest {
        expectCorrectHttpMethod(HttpMethod.GET)
    }

    @Test
    fun test_request_setsHttpMethodToPut() = runTest {
        expectCorrectHttpMethod(HttpMethod.PUT)
    }

    @Test
    fun test_request_setsHttpMethodToPost() = runTest {
        expectCorrectHttpMethod(HttpMethod.POST)
    }

    @Test
    fun test_request_setsHttpMethodToDelete() = runTest {
        expectCorrectHttpMethod(HttpMethod.DELETE)
    }

    @Test
    fun test_request_encodesUrlCorrectly() = runTest {
        val responseJson = "{\"id\": 1, \"name\": \"name\"}"
        val mockEngine = MockEngine {
            respond(responseJson)
        }
        val sut = makeSUT(mockEngine, testBaseUrl)

        sut.request(TestHttpRequest(endpoint = testEndpoint))

        assertEquals(1, mockEngine.requestHistory.size)
        assertEquals(testBaseUrl + testEndpoint, mockEngine.requestHistory.first().url.toString())
    }

    @Test
    fun test_request_failsUrlEncoding() = runTest {
        val responseJson = "{\"id\": 1, \"name\": \"name\"}"
        val mockEngine = MockEngine {
            respond(responseJson)
        }
        val sut = makeSUT(mockEngine, baseUrl = unparsableUrl)

        assertFailsWith<UrlEncodingException> {
            sut.request(TestHttpRequest(endpoint = unparsableUrl))
        }
    }

    @Test
    fun test_request_addsQueryParamsCorrectly() = runTest {
        val responseJson = "{\"id\": 1, \"name\": \"name\"}"
        val mockEngine = MockEngine {
            respond(responseJson)
        }
        val sut = makeSUT(mockEngine, baseUrl = testBaseUrl)

        val expectedQueryParams = QueryParams()
        expectedQueryParams.put("key1", "value1")
        expectedQueryParams.put("key2", listOf("value1", "value2"))

        sut.request(TestHttpRequest(params = expectedQueryParams))

        assertEquals(1, mockEngine.requestHistory.size)
        assertEquals(
            expectedQueryParams.parameters.size,
            mockEngine.requestHistory.first().url.parameters.entries().size
        )
        expectedQueryParams.parameters.forEach { expectedParam ->
            assertEquals(expectedParam.value, mockEngine.requestHistory.first().url.parameters[expectedParam.key])
        }

    }

    @Test
    fun test_request_addsRequestBody() = runTest {
        val mockEngine = MockEngine {
            respond("any content")
        }

        val expectedRequestBody = "any body"

        val sut = makeSUT(mockEngine, baseUrl = testBaseUrl)

        sut.request(TestHttpRequest(body = expectedRequestBody))

        assertEquals(1, mockEngine.requestHistory.size)

        val actualRequestBody = (mockEngine.requestHistory.first().body as TextContent).text

        assertEquals(expectedRequestBody, actualRequestBody)
    }

    @Test
    fun test_request_setsResponseAsSuccessful() = runTest {
        val mockEngine = MockEngine {
            respondOk("any content")
        }
        val sut = makeSUT(mockEngine, baseUrl = testBaseUrl)

        val response = sut.request(TestHttpRequest())

        assertTrue(response.isSuccessful)
    }

    @Test
    fun test_request_setsResponseAsUnsuccessful() = runTest {
        val mockEngine = MockEngine {
            respondError(HttpStatusCode.Unauthorized)
        }
        val sut = makeSUT(mockEngine, baseUrl = testBaseUrl)

        val response = sut.request(TestHttpRequest())

        assertFalse(response.isSuccessful)
    }

    @Test
    fun test_request_setsStatusCodeCorrectly() = runTest {
        val testCases = listOf(
            HttpStatusCode.OK,
            HttpStatusCode.NotFound,
            HttpStatusCode(100000, "test")
        )

        testCases.forEach { status ->
            val mockEngine = MockEngine {
                respond("any content", status)
            }
            val sut = makeSUT(mockEngine, baseUrl = testBaseUrl)

            val response = sut.request(TestHttpRequest())

            assertEquals(status.value, response.code)
        }
    }

    @Test
    fun test_request_setsResponseBodyAsString() = runTest {
        val responseJson = "{\"id\": 1, \"name\": \"name\"}"
        val mockEngine = MockEngine {
            respond(responseJson)
        }
        val sut = makeSUT(mockEngine, baseUrl = testBaseUrl)

        val response = sut.request(TestHttpRequest())

        assertEquals(responseJson, response.body)
    }

    @Test
    fun test_request_throwsCoroutinesCancellationExceptionOnCancel() = runTest {
        expectCoroutinesCancellationException(request = { sut ->
            sut.request(TestHttpRequest())
        }, coroutineScope = this)
    }

    private fun makeSUT(mockEngine: MockEngine, baseUrl: String): HttpClient {
        return KtorHttpClient(io.ktor.client.HttpClient(mockEngine), baseUrl)
    }

    private suspend fun expectCorrectHttpMethod(
        method: HttpMethod,
    ) {
        val responseJson = "{\"id\": 1, \"name\": \"name\"}"
        val mockEngine = MockEngine {
            respond(responseJson)
        }
        val sut = makeSUT(mockEngine, testBaseUrl)

        sut.request(TestHttpRequest(method = method))

        assertEquals(1, mockEngine.requestHistory.size)
        assertEquals(method.toKtor(), mockEngine.requestHistory.first().method)

    }

    private suspend fun expectCoroutinesCancellationException(
        request: suspend (HttpClient) -> Any,
        coroutineScope: CoroutineScope
    ) {
        val mockEngine = MockEngine {
            println("request")
            respond("any response")
        }
        val sut = makeSUT(mockEngine, testBaseUrl)

        val job = coroutineScope.launch {
            println("do job")
            assertFailsWith<CancellationException> {
                request(sut)
            }
        }

        job.cancelAndJoin()

        assertTrue(job.isCancelled)
    }

    //region HELPERS

    private val testBaseUrl = "https://test.com/"
    private val testEndpoint = "test"
    private val unparsableUrl = "scheme://user:complex#password@host:1234/path"

    private class TestHttpRequest(
        override val method: HttpMethod = HttpMethod.GET,
        override val endpoint: String = "test",
        override val body: String? = null,
        override val params: QueryParams? = null,
        override val headers: Map<String, String> = mapOf()
    ) : HttpRequest()

    //endregion
}
