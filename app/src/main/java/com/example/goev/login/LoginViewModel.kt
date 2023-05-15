package com.example.goev.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserDatabase
import com.example.goev.database.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel (application: Application) : AndroidViewModel(application){

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun loginValidation(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUserByEmailAndPassword(email, password)
            if (user != null) {
                repository.updateUserLoggedIn(userId = user.id, is_logged_in = true)
            }
            val result = user != null
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }



}