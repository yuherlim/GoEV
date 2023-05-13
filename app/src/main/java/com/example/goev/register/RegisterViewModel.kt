package com.example.goev.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserDatabase
import com.example.goev.database.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun addUser(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userData)
        }
    }

    fun validatePassword(pass1: String, pass2: String): Boolean {
        return pass1 == pass2
    }

    fun isUserNameExists(userName: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isUserNameExists(userName)
            callback(result)
        }
    }

    fun isUserEmailExists(email: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isUserEmailExists(email)
            callback(result)
        }
    }
}

