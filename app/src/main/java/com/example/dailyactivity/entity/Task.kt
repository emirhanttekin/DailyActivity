package com.example.dailyactivity.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val startDate: String, // Başlangıç Tarihi (dd/MM/yyyy formatında)
    val startTime: String, // Başlangıç Zamanı (HH:mm formatında)
    val endTime: String, // Bitiş Zamanı (HH:mm formatında)
    val description: String,
    val type: String, // Tip (örneğin: iş, kişisel, alışveriş, vs.)
    val tags: String // Etiketler (virgülle ayrılmış, örneğin: önemli, acil, vs.)
)
