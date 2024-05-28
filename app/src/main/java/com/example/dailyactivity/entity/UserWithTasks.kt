package com.example.dailyactivity.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(tableName = "user_with_tasks")
data class UserWithTasks(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(UserTaskCrossRef::class, parentColumn = "userId", entityColumn = "taskId")
    )
    val tasks: List<Task>
)