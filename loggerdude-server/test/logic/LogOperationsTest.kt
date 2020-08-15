package com.augustg.logic

import com.augustg.Env
import com.augustg.model.FormattedLog
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import java.io.FileReader
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Tests for [LogOperations]
 */
class LogOperationsTest {

    private val serializer = Json(JsonConfiguration.Stable)

    companion object {

        @BeforeClass
        @JvmStatic
        fun init() {
            Env.DEBUG = true
        }

        @AfterClass
        @JvmStatic
        fun cleanUp() {
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
     * Tests that logs are stored properly
     */
    @Test
    fun testStoreLogs() {
        // read JSON from test file and parse
        val json = FileReader("./test/TestLogs.json").readText()
        assertNotNull(json)
        val logs = serializer.parse(FormattedLog.serializer().list, json)


        // store logs
        LogOperations.storeLogs(logs)

        // check that logs match local storage
        val storedLogs = LogOperations.fetchLogs()
        assertEquals(json, serializer.stringify(FormattedLog.serializer().list, storedLogs))
    }

    /**
     * Tests that logs are cleared properly
     */
    @Test
    fun testClearLogs() {
        // read JSON from test file
        val json = FileReader("./test/TestLogs.json").readText()
        assertNotNull(json)

        // store logs locally
        LogOperations.storeLogs(serializer.parse(FormattedLog.serializer().list, json))

        // clear logs
        LogOperations.clearLogs()

        // fetch logs
        val fetchedLogs = LogOperations.fetchLogsFormatted()

        // check that logs are cleared
        assertEquals(
            expected = "no logs",
            actual = fetchedLogs
        )
    }

    /**
     * Tests that logs are accessed properly
     */
    @Test
    fun testAccessLogs() {
        // read JSON from test file
        val json = FileReader("./test/TestLogs.json").readText()
        assertNotNull(json)

        // store logs locally
        LogOperations.storeLogs(serializer.parse(FormattedLog.serializer().list, json))

        // fetch logs
        val fetchedLogs = LogOperations.fetchLogsFormatted()

        // check that logs are correct
        assertEquals(
            expected = "Aug 5, 2020 12:43:53 AM   Cleared Logs\n" +
                    "Aug 5, 2020 12:43:54 AM   Try It Out! clicked",
            actual = fetchedLogs
        )
    }
}