package com.example.goev.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserRepository
import com.example.goev.databases.TipsAndKnowledgeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel (application: Application) : AndroidViewModel(application){

    private val repository: UserRepository

    init {
        val userDao = TipsAndKnowledgeDatabase.getInstance(application).userDAO
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

    fun updateAllUsersLoggedOut(){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateAllUsersLoggedOut()
        }
    }



}