package com.augustg.rluda.library

import android.content.Context
import androidx.lifecycle.LiveData
import com.augustg.rluda.library.logs.FormattedLog
import com.augustg.rluda.library.storage.LogDatabase
import com.augustg.rluda.library.storage.StorageManager

/**
 * Logger Dude API
 * Initialize with a Context before using
 *
 * @throws Exception if used before calling [initialize]
 */
object LoggerDude {

    private var initialized = false

    private lateinit var storageManager: StorageManager

    /**
     * Verifies that LoggerDude has been initialized
     *
     * @throws Exception if used before initialization
     */
    private fun verifyInitialization() {
        if (!initialized) throw Exception("LoggerDude must be initialized before use")
    }

    /**
     * Initializes object by creating a [StorageManager] to access local storage
     *
     * @param context
     */
    fun initialize(context: Context) {
        storageManager = StorageManager(LogDatabase.getInstance(context).logDao())
        initialized = true
    }

    /**
     * Clears all stored Logs
     */
    fun clear() {
        verifyInitialization()
        storageManager.clearLogs()
    }

    /**
     * Stores a Log message
     *
     * @param message
     */
    fun log(message: String) {
        verifyInitialization()
        storageManager.storeLog(message)
    }

    /**
     * Does something with the stored Logs
     *
     * @param doThis task to perform
     */
    fun withLogs(doThis: (logs: List<FormattedLog>) -> Unit) {
        verifyInitialization()
        storageManager.pullLogs { doThis(it) }
    }

    /**
     * Gets Logs as a LiveData stream
     *
     * @return LiveData list of Logs
     */
    fun live(): LiveData<List<FormattedLog>> {
        verifyInitialization()
        return storageManager.observeLogs()
    }
}
