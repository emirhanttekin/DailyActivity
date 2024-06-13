package com.example.dailyactivity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dailyactivity.entity.Task
import kotlinx.coroutines.flow.Flow

// TaskDao.kt
@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>



    @Query("SELECT * FROM tasks WHERE userId = :userId")
    fun getTasksForUser(userId: Int): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)


}

