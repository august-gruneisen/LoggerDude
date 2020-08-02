package com.augustg.rluda.library

import android.content.Context
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
     * Initializes Logger Dude by creating a [StorageManager] to access local storage
     *
     * @param context
     */
    fun initialize(context: Context) {
        storageManager = StorageManager(
            LogDatabase.getInstance(context).logDao()
        )
        initialized = true
    }

    fun method() {
        if (!initialized) throw Exception("LoggerDude must be initialized before use")
    }
}
