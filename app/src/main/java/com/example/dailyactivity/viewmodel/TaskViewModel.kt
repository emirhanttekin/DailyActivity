package com.example.dailyactivity.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.dailyactivity.entity.Task
import com.example.dailyactivity.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _userId = MutableLiveData<Int>()

    val allTasks: LiveData<List<Task>> = _userId.switchMap { userId ->
        if (userId == -1) {
            repository.getAllTasks().asLiveData()
        } else {
            repository.getTasksByUserId(userId).asLiveData()
        }
    }





    fun getTasksForToday(userId: Int): LiveData<List<Task>> {
        return repository.getTasksByUserId(userId).map { tasks ->
            tasks.filter { it.isToday() }
        }.asLiveData()
    }

    fun getTasksForThisWeek(userId: Int): LiveData<List<Task>> {
        return repository.getTasksByUserId(userId).map { tasks ->
            tasks.filter { it.isThisWeek() }
        }.asLiveData()
    }

    fun getTasksForAll(userId: Int): LiveData<List<Task>> {
        return repository.getTasksByUserId(userId).asLiveData()
    }

    fun setUserId(userId: Int) {
        _userId.value = userId
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertTask(task)
                Log.d("TaskViewModel", "Task inserted successfully.")
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error inserting task: ${e.message}")
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(taskId)
        }
    }

    fun getTaskCountForToday(userId: Int): LiveData<Int> {
        return repository.getTasksByUserId(userId).map { tasks ->
            tasks.count { it.isToday() }
        }.asLiveData()
    }

    fun getTaskCountForThisWeek(userId: Int): LiveData<Int> {
        return repository.getTasksByUserId(userId).map { tasks ->
            tasks.count { it.isThisWeek() }
        }.asLiveData()
    }

    fun getTaskCountForThisYear(userId: Int): LiveData<Int> {
        return repository.getTasksByUserId(userId).map { tasks ->
            tasks.count { it.isThisYear() }
        }.asLiveData()
    }
}

