package com.example.dailyactivity.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val startDate: String, // Başlangıç Tarihi (dd/MM/yyyy formatında)
    val startTime: String, // Başlangıç Zamanı (HH:mm formatında)
    val endTime: String, // Bitiş Zamanı (HH:mm formatında)
    val description: String,
    val type: String, // Tip (örneğin: iş, kişisel, alışveriş, vs.)
    val tags: String // Etiketler (virgülle ayrılmış, örneğin: önemli, acil, vs.)
) {
    fun isToday(): Boolean {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val taskDate = Calendar.getInstance().apply {
            time = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this@Task.startDate) ?: return false
        }
        return today.get(Calendar.YEAR) == taskDate.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == taskDate.get(Calendar.DAY_OF_YEAR)
    }

    fun isThisWeek(): Boolean {
        val today = Calendar.getInstance()
        val taskDate = Calendar.getInstance().apply {
            time = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this@Task.startDate) ?: return false
        }

        val startOfWeek = today.clone() as Calendar
        startOfWeek.set(Calendar.DAY_OF_WEEK, today.firstDayOfWeek)
        startOfWeek.set(Calendar.HOUR_OF_DAY, 0)
        startOfWeek.set(Calendar.MINUTE, 0)
        startOfWeek.set(Calendar.SECOND, 0)
        startOfWeek.set(Calendar.MILLISECOND, 0)

        val endOfWeek = startOfWeek.clone() as Calendar
        endOfWeek.add(Calendar.DAY_OF_WEEK, 6)
        endOfWeek.set(Calendar.HOUR_OF_DAY, 23)
        endOfWeek.set(Calendar.MINUTE, 59)
        endOfWeek.set(Calendar.SECOND, 59)
        endOfWeek.set(Calendar.MILLISECOND, 999)

        return !taskDate.before(startOfWeek) && !taskDate.after(endOfWeek)
    }
}
