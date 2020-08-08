package com.augustg.loggerdude.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representation of a Log to be persisted locally
 *
 * @param time of Log in milliseconds since midnight, January 1, 1970 UTC
 * @param message
 */
@Entity(tableName = "log_table")
data class LogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "message") val message: String
)