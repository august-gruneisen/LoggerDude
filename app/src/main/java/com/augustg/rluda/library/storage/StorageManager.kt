package com.augustg.rluda.library.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.augustg.rluda.library.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Repository wrapping the [LogDatabase]
 *
 * @param logDao Data Access Object for accessing local storage
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
     * Pulls a list of Logs and does something with them on the main thread
     *
     * @param since (optional) will pull only Logs starting from the specified time
     * in milliseconds since midnight, January 1, 1970 UTC
     * @param doThis some task to perform with the Logs
     * @return a list of [Log] data objects
     */
    fun withLogs(since: Long = 0, doThis: (logs: List<Log>) -> Unit) {
        scope.launch {
            val logs = when (since) {
                0.toLong() -> {
                    logDao.queryAllLogs()
                }
                else -> {
                    logDao.queryLogsSince(since)
                }
            }.map { entity ->
                Log(entity.time, entity.message)
            }

            withContext(Dispatchers.Main) {
                doThis.invoke(logs)
            }
        }
    }

    /**
     * Returns an observable LiveData list of Logs
     *
     * @return LiveData list of locally stored logs
     */
    fun observeLogs(): LiveData<List<Log>> {
        return Transformations.map(logDao.getLogsLiveData()) { entities ->
            entities.map { Log(it.time, it.message) }
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