package com.example.dailyactivity.repository

import android.util.Log
import com.example.dailyactivity.dao.TaskDao
import com.example.dailyactivity.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTasksByUserId(userId: Int): Flow<List<Task>> {
        return taskDao.getTasksForUser(userId)
    }

    suspend fun insertTask(task: Task) {
        try {
            taskDao.insert(task)
            Log.d("TaskRepository", "Task inserted successfully: $task")
        } catch (e: Exception) {
            Log.e("TaskRepository", "Error inserting task: ${e.message}")
        }
    }
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }
}
