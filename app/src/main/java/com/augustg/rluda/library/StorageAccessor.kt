package com.augustg.rluda.library

import android.content.Context
import com.augustg.rluda.library.storage.LogDao
import com.augustg.rluda.library.storage.LogDatabase
import com.augustg.rluda.library.storage.LogEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A repository wrapping the full local storage of Logs
 *
 * @throws Exception if used without being initialized in [initialize]
 */
object StorageAccessor {

    private var isInitialized = false

    private lateinit var logDao: LogDao

    private lateinit var scope: CoroutineScope

    /**
     * [StorageAccessor] must be initialized with a Context before it can be used
     *
     * @param context can be any context in the application
     */
    fun initialize(context: Context) {
        logDao = LogDatabase.getInstance(context).logDao()
        scope = CoroutineScope(Dispatchers.IO)
        isInitialized = true
    }

    /**
     * Stores a Log message into the local storage
     *
     * @param message
     *
     * @throws Exception if StorageAccessor has not been initialized
     */
    fun storeLog(message: String) {
        if (!isInitialized) throw Exception("StorageAccessor must be initialized before use")

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
     * Pulls a list of Logs from the local storage and then
     * does something with them on the main thread
     *
     * @param since (optional) will pull  only the Logs starting from the specified time
     * in milliseconds since midnight, January 1, 1970 UTC
     * @param andThen some task to perform on the main thread
     * @return a list of [Log] data objects
     */
    fun pullLogs(since: Long = 0, andThen: (logs: List<Log>) -> Unit) {
        if (!isInitialized) throw Exception("StorageAccessor must be initialized before use")

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
                andThen.invoke(logs)
            }
        }
    }

    /**
     * Clears all Logs from the local storage
     */
    fun clearLogs() {
        if (!isInitialized) throw Exception("StorageAccessor must be initialized before use")

        scope.launch {
            logDao.deleteAllLogs()
        }
    }
}