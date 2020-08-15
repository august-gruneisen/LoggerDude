package com.augustg.logic

import com.augustg.model.FormattedLog
import com.augustg.storage.LogDatabase
import org.jetbrains.annotations.TestOnly

/**
 * Logic for interacting with stored logs
 */
object LogOperations {

    /**
     * Stores logs
     *
     * @param logs
     */
    fun storeLogs(logs: List<FormattedLog>) = LogDatabase.insertLogs(logs)

    /**
     * Fetches logs and formats them
     *
     * @return formatted logs or "no logs"
     */
    fun fetchLogsFormatted() = when (
        val logs = LogDatabase.findLogs()
            .joinToString("\n") {
            "${it.timestamp}   ${it.message}"
        })
    {
        "" -> "no logs"
        else -> logs
    }

    /**
     * Fetches logs
     * @return list of log objects
     */
    @TestOnly
    fun fetchLogs(): List<FormattedLog> = LogDatabase.findLogs()

    /**
     * Clears stored logs
     */
    fun clearLogs() = LogDatabase.dropAll()
}