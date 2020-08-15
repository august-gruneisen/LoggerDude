package com.augustg

import com.augustg.TestHelper.loadTestJson
import com.augustg.logic.LogOperations
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.AfterClass
import org.junit.BeforeClass
import kotlin.test.*

/**
 * Integration tests for [main]
 */
class ApplicationTest {

    private val testJson = loadTestJson()

    companion object {

        @BeforeClass
        @JvmStatic
        fun init() {
            Env.DEBUG = true
        }

        @AfterClass
        @JvmStatic
        fun cleanUp() {
            Env.DEBUG = false
            LogOperations.clearLogs() // Drops test collection
        }
    }

    /**
     * Run this method before each test
     */
    @BeforeTest
    fun setup() {
        LogOperations.clearLogs()
    }

    /**
     * Tests that the root path redirects to login
     */
    @Test
    fun testRootPath() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Get, Endpoints.ROOT)) {
            assert(requestHandled)
            assertEquals(Urls.LOGIN, response.headers[HttpHeaders.Location])
        }
    }

    /**
     * Tests that the login path unauthenticated is handled
     */
    @Test
    fun testLoginPathUnauthenticated() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Get, Endpoints.LOGIN)) {
            assert(requestHandled)
        }
    }

    /**
     * Tests that posting logs unauthenticated returns 200
     */
    @Test
    fun testPostLogs() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Post, Endpoints.LOG) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(testJson)
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    /**
     * Tests that accessing console unauthorized redirects to login
     */
    @Test
    fun testUnauthorizedConsoleAccess() = withTestApplication(Application::main) {
        cookiesSession {
            with(handleRequest(HttpMethod.Get, Endpoints.ROOT)) {
                assertEquals(HttpStatusCode.Found, response.status())
                assertEquals(Urls.LOGIN, response.headers[HttpHeaders.Location])
            }
        }
    }

    /**
     * Tests that accessing console authorized responds with 200
     */
    @Test
    fun testAuthorizedConsoleAccess() = withTestApplication(Application::main) {
        cookiesSession {
            authenticate()
            with(handleRequest(HttpMethod.Get, Endpoints.ROOT)) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    /**
     * Tests that getting logs before they are posted returns 200
     */
    @Test
    fun testGetLogsBeforeAnyExist() = withTestApplication(Application::main) {
        cookiesSession {
            authenticate()
            with(handleRequest(HttpMethod.Get, Endpoints.ROOT)) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    /**
     * Tests that getting logs after posting returns 200
     */
    @Test
    fun testGetLogsAfterPosting() = withTestApplication(Application::main) {
        cookiesSession {
            authenticate()
            // post logs
            with(handleRequest(HttpMethod.Post, Endpoints.LOG) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(testJson)
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            // get logs
            with(handleRequest(HttpMethod.Get, Endpoints.ROOT)) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    /**
     * Tests that clearing logs redirects to console
     */
    @Test
    fun testClearLogsRedirect() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Get, Endpoints.CLEAR)) {
            assertEquals(HttpStatusCode.Found, response.status())
            assertEquals(Endpoints.LOGGERDUDE, response.headers[HttpHeaders.Location])
        }
    }

    /**
     * Tests that clearing logs after posting returns 200
     */
    @Test
    fun testClearLogsAfterPosting() = withTestApplication(Application::main) {
        // post logs
        with(handleRequest(HttpMethod.Post, Endpoints.LOG) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(testJson)
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
        }

        // clear logs
        with(handleRequest(HttpMethod.Get, Endpoints.CLEAR)) {
            assertEquals(HttpStatusCode.Found, response.status())
        }
    }

    /**
     * Authenticates using a mock user/password
     */
    private fun TestApplicationEngine.authenticate() {
        with(handleRequest(HttpMethod.Get, Endpoints.LOGIN)) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        with(handleRequest(HttpMethod.Post, Endpoints.LOGIN) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            val mockCreds = Pair(Credentials.username, Credentials.password)
            setBody(listOf("username" to mockCreds.first, "password" to mockCreds.second).formUrlEncode())
        }) {
            assertEquals(Endpoints.LOGGERDUDE, response.headers[HttpHeaders.Location])
        }
    }
}