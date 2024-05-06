package com.example.dailyactivity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailyactivity.entity.User


@Dao
interface UserDao {
    @Insert
    suspend fun insert(user : User)

    @Query("SELECT * FROM users WHERE username = :username  AND password = :password")
    suspend fun login(username: String, password: String) : User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE phone = :phone")
    suspend fun getUserByPhone(phone: String): User?

    @Query("SELECT * FROM users Where  id = :id")
    suspend fun  getUserById(id: Int): User?



}