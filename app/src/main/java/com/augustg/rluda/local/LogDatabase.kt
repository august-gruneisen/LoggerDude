package com.augustg.rluda.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LogEntity::class], version = 1)
abstract class LogDatabase : RoomDatabase() {

    abstract fun logDao(): LogDao

    companion object {
        @Volatile
        private var INSTANCE: LogDatabase? = null

        fun getInstance(context: Context): LogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LogDatabase::class.java,
                    "log_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
