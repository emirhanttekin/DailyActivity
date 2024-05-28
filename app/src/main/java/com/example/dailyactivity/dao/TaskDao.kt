package com.example.dailyactivity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailyactivity.entity.Task
@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Query("UPDATE tasks SET title = :title, startDate = :startDate, startTime = :startTime, endTime = :endTime, description = :description, type = :type, tags = :tags WHERE id = :taskId")
    suspend fun updateTask(taskId: Int, title: String, startDate: String, startTime: String, endTime: String, description: String, type: String, tags: String)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}