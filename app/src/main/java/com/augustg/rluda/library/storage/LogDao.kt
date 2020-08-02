package com.augustg.rluda.library.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Data Access Object for locally stored Logs
 *
 * @see LogDatabase
 * @see LogEntity
 */
@Dao
interface LogDao {

    /**
     * Inserts a Log into [LogDatabase]
     *
     * @param logEntity [LogEntity]
     */
    @Insert
    fun insertLog(logEntity: LogEntity)

    /**
     * Queries [LogDatabase] for all of the Logs since a given time
     *
     * @param time milliseconds since midnight, January 1, 1970 UTC
     * @return a list of Logs since the time specified
     */
    @Query("SELECT * FROM log_table WHERE time = :time")
    suspend fun queryLogsSince(time: Long): List<LogEntity>

    /**
     * Queries [LogDatabase] for all of the locally stored Logs
     *
     * @return a list of all stored Logs
     */
    @Query("SELECT * FROM log_table")
    suspend fun queryAllLogs(): List<LogEntity>

    /**
     * Gets all locally stored Logs as an observable LiveData
     *
     * @return a LiveData list of all Logs
     */
    @Query("SELECT * FROM log_table")
    fun getLogsLiveData(): LiveData<List<LogEntity>>

    /**
     * Deletes all locally stored Logs
     */
    @Query("DELETE FROM log_table")
    fun deleteAllLogs()
}
