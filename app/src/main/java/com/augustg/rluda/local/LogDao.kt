package com.augustg.rluda.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LogDao {

    @Insert
    fun insertLog(logEntity: LogEntity)

    @Query("SELECT * FROM log_table WHERE id = :id")
    fun getLog(id: Int): LogEntity

    @Query("SELECT * FROM log_table")
    fun getAllLogs(): List<LogEntity>
}
