package com.example.dailyactivity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailyactivity.dao.TaskDao
import com.example.dailyactivity.dao.UserDao
import com.example.dailyactivity.dao.UserWithTasksDao
import com.example.dailyactivity.entity.Task
import com.example.dailyactivity.entity.User
import com.example.dailyactivity.entity.UserTaskCrossRef

// AppDatabase.kt
@Database(entities = [Task::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "daily_activity_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

