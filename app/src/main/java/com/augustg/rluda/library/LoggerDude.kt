package com.augustg.rluda.library

import android.content.Context
import androidx.lifecycle.LiveData
import com.augustg.rluda.library.logs.FormattedLog
import com.augustg.rluda.library.storage.LogDatabase
import com.augustg.rluda.library.storage.StorageManager

/**
 * Logger Dude API
 *
 * @throws Exception if used before calling [initialize]
 */
object LoggerDude {

    private var initialized = false

    private lateinit var storageManager: StorageManager

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
     *
     * @throws Exception if used before initialization
     */
    fun clear() {
        if (!initialized) throw Exception("LoggerDude must be initialized before use")

        storageManager.clearLogs()
    }

    /**
     * Stores a Log message
     *
     * @param message
     * @throws Exception if used before initialization
     */
    fun log(message: String) {
        if (!initialized) throw Exception("LoggerDude must be initialized before use")

        storageManager.storeLog(message)
    }

    /**
     * Does something with the stored Logs
     *
     * @param doThis task to perform
     * @throws Exception if used before initialization
     */
    fun withLogs(doThis: () -> Unit) {
        if (!initialized) throw Exception("LoggerDude must be initialized before use")

        storageManager.pullLogs { doThis.invoke() }
    }

    /**
     * Gets Logs as a LiveData stream
     *
     * @return LiveData list of Logs
     * @throws Exception if used before initialization
     */
    fun live(): LiveData<List<FormattedLog>> {
        if (!initialized) throw Exception("LoggerDude must be initialized before use")

        return storageManager.observeLogs()
    }
}
