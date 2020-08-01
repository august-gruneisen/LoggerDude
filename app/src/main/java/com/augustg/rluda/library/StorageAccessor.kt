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
 * Repository wrapping the full local storage of Logs
 *
 * @throws Exception if used before calling [initialize]
 */
object StorageAccessor {

    private var initialized = false

    private lateinit var logDao: LogDao

    private lateinit var scope: CoroutineScope

    /**
     * [StorageAccessor] must be initialized before it can be used
     *
     * @param context
     */
    fun initialize(context: Context) {
        logDao = LogDatabase.getInstance(context).logDao()
        scope = CoroutineScope(Dispatchers.IO)
        initialized = true
    }

    /**
     * Stores a Log message in the local storage
     *
     * @param message
     * @throws Exception if StorageAccessor has not been initialized
     */
    fun storeLog(message: String) {
        if (!initialized) throw Exception("StorageAccessor must be initialized before use")

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
     * @throws Exception if StorageAccessor has not been initialized
     */
    fun pullLogs(since: Long = 0, andThen: (logs: List<Log>) -> Unit) {
        if (!initialized) throw Exception("StorageAccessor must be initialized before use")

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
     *
     * @throws Exception if StorageAccessor has not been initialized
     */
    fun clearLogs() {
        if (!initialized) throw Exception("StorageAccessor must be initialized before use")

        scope.launch {
            logDao.deleteAllLogs()
        }
    }
}
