package com.example.dailyactivity.application

import android.app.Application
import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.repository.TaskRepository
import com.example.dailyactivity.repository.UserRepository



class DailyApplication : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val userDao by lazy { database.userDao() }
    val taskRepository by lazy { TaskRepository(database.taskDao()) }

}

