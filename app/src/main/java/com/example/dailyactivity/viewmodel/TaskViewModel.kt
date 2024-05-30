package com.example.dailyactivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dailyactivity.entity.Task
import com.example.dailyactivity.repository.TaskRepository
import com.example.dailyactivity.repository.UserRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val tasks: LiveData<List<Task>> = repository.getAllTasks().asLiveData()

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }
}