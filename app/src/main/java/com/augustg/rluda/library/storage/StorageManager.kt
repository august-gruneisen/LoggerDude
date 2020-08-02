package com.augustg.rluda.library.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.augustg.rluda.library.FormattedLog
import com.augustg.rluda.library.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Repository wrapping [LogDatabase]
 *
 * @property logDao DAO for accessing local storage
 */
class StorageManager(private val logDao: LogDao) {

    private val scope = CoroutineScope(Dispatchers.IO)

    /**
     * Stores a Log message
     *
     * @param message
     */
    fun storeLog(message: String) {
        scope.launch {
            logDao.insertLog(
                LogEntity(
                    time = System.currentTimeMillis(),
                    message = message
                )
            )
        }
    }

    /**
     * Pulls a list of formatted Logs and does something with them on the main thread
     *
     * @param since (optional) starts from the specified time in millis since January 1, 1970 UTC
     * @param doThis some task to perform with the formatted Logs
     */
    fun pullLogs(since: Long = 0, doThis: (logs: List<FormattedLog>) -> Unit) {
        scope.launch {
            val logs = when (since) {
                0.toLong() -> {
                    logDao.queryAllLogs()
                }
                else -> {
                    logDao.queryLogsSince(since)
                }
            }.map {
                FormattedLog.format(
                    Log(it.time, it.message)
                )
            }

            withContext(Dispatchers.Main) {
                doThis.invoke(logs)
            }
        }
    }

    /**
     * Returns an observable LiveData stream of formatted Logs
     *
     * @return LiveData list of formatted Logs
     */
    fun observeLogs(): LiveData<List<FormattedLog>> {
        return Transformations.map(logDao.getLogsLiveData()) { entities ->
            entities.map {
                FormattedLog.format(
                    Log(it.time, it.message)
                )
            }
        }
    }

    /**
     * Clears all stored Logs
     */
    fun clearLogs() {
        scope.launch {
            logDao.deleteAllLogs()
        }
    }
}