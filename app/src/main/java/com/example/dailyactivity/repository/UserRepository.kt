package com.example.dailyactivity.repository

import com.example.dailyactivity.dao.UserDao
import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.entity.User

class UserRepository(private val db: AppDatabase) {
    suspend fun getUserByEmail(email: String): User? {
        return db.userDao().getUserByEmail(email)
    }

    suspend fun getUserById(userId: Int): User? {
        return db.userDao().getUserById(userId)
    }

    suspend fun updateUser(user: User) {
        db.userDao().update(user)
    }
    suspend fun updatePhoto(userId: Int, photo: String) {
        db.userDao().updatePhoto(userId, photo)
    }
}
