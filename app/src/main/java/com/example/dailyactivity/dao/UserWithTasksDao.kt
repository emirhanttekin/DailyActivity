package com.example.dailyactivity.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.dailyactivity.entity.UserWithTasks

@Dao
interface UserWithTasksDao {
    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithTasks(userId: Int): UserWithTasks?
}