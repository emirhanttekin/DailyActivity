package com.example.dailyactivity.repository

import com.example.dailyactivity.dao.TaskDao
import com.example.dailyactivity.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getTasksByUserId(userId: Int): Flow<List<Task>> {
        return taskDao.getTasksForUser(userId)
    }

    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getTasksForUser(-1) // Adjust this to get all tasks if needed
    }
}
