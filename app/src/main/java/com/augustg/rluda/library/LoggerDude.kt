package com.augustg.rluda.library

import android.content.Context
import androidx.lifecycle.LiveData
import com.augustg.rluda.library.logs.FormattedLog
import com.augustg.rluda.library.outbound.NetworkCallback
import com.augustg.rluda.library.outbound.NetworkManager
import com.augustg.rluda.library.storage.LogDatabase
import com.augustg.rluda.library.storage.StorageManager

/**
 * LoggerDude API
 * Initialize with a Context before using
 *
 * @throws Exception if used before calling [initialize]
 */
object LoggerDude {

    private var initialized = false

    private lateinit var storageManager: StorageManager

    private lateinit var networkManager: NetworkManager

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
     * @param endpoint (optional) specify an API to dump Logs
     */
    fun initialize(context: Context, endpoint: String) {
        storageManager = StorageManager(LogDatabase.getInstance(context).logDao())
        networkManager = NetworkManager(endpoint)
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
     * Gets Logs as a LiveData stream
     *
     * @return LiveData list of Logs
     */
    fun live(): LiveData<List<FormattedLog>> {
        verifyInitialization()
        return storageManager.observeLogs()
    }

    /**
     * Does something with the stored Logs
     *
     * @param doThis task to perform
     */
    private fun withLogs(doThis: (logs: List<FormattedLog>) -> Unit) {
        verifyInitialization()
        storageManager.pullLogs { doThis(it) }
    }

    /**
     * Sends the stored Logs to a remote server
     *
     * @param callback (optional) [NetworkCallback]
     */
    fun dump(callback: NetworkCallback? = null) {
        withLogs {
            networkManager.sendLogs(it, callback)
        }
    }
}