package com.example.dailyactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dailyactivity.entity.User
import com.example.dailyactivity.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.xml.transform.Result

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    sealed class Result {
        data class Success(val user: User) : Result()
        data class Error(val message: String) : Result()
    }

    fun login(email: String, password: String, callback: (Result) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)
            if (user != null && user.password == password) {
                callback(Result.Success(user))
            } else {
                callback(Result.Error("Invalid email or password"))
            }
        }
    }
}

