package com.example.dailyactivity.entity

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "taskId"])
data class UserTaskCrossRef(
    val userId: Int,
    val taskId: Int
)
