package com.example.dailyactivity.repository


import com.example.dailyactivity.dao.TaskDao

import com.example.dailyactivity.entity.Task

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }
}