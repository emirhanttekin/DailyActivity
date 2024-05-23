package com.example.dailyactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyactivity.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.xml.transform.Result

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun login(email: String, password: String, callback: (Result) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            withContext(Dispatchers.Main) {
                if (user == null) {
                    callback(Result.Error("Email Yanlış Girdiniz Tekrar Deneyin"))
                } else if (user.password != password) {
                    callback(Result.Error("Şifre Yanlış Tekrar Deneyin"))
                } else {
                    callback(Result.Success)
                }
            }
        }
    }

    sealed class Result {
        object Success : Result()
        data class Error(val message: String) : Result()
    }
}