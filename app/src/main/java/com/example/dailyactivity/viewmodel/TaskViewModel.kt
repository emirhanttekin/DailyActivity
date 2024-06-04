package com.example.dailyactivity.viewmodel

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

    val todayTasks: LiveData<List<Task>> = _userId.switchMap { userId ->
        repository.getTasksByUserId(userId).map { tasks ->
            tasks.filter { it.isToday() }
        }.asLiveData()
    }

    val thisWeekTasks: LiveData<List<Task>> = _userId.switchMap { userId ->
        repository.getTasksByUserId(userId).map { tasks ->
            tasks.filter { it.isThisWeek() }
        }.asLiveData()
    }

    fun setUserId(userId: Int) {
        _userId.value = userId
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
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
}

