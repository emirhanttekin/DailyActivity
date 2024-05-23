package com.example.dailyactivity.repository

import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.entity.User

class UserRepository(private  val  db :  AppDatabase) {
    suspend fun getUserByEmail(email:String): User? {
        return db.userDao().getUserByEmail(email)

    }
}