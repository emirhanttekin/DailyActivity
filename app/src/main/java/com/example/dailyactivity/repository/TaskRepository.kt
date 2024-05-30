package com.example.dailyactivity.repository


import com.example.dailyactivity.dao.TaskDao

import com.example.dailyactivity.entity.Task

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }
}
