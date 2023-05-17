package com.example.goev.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserRepository
import com.example.goev.databases.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel (application: Application) : AndroidViewModel(application){

    private val repository: UserRepository

    val users = listOf<UserData> (
        UserData(0, "admin", "adminGoEV@gmail.com",  "admin",  ByteArray(0), "Admin", "", false, true),
        UserData(0, "user1", "user1@gmail.com",  "12345",  ByteArray(0), "User 1", "", false, false),
        UserData(0, "user2", "user2@gmail.com",  "12345",  ByteArray(0), "User 2", "", false, false),
        UserData(0, "user3", "user3@gmail.com",  "12345",  ByteArray(0), "User 3", "", false, false),
        UserData(0, "user4", "user4@gmail.com",  "12345",  ByteArray(0), "User 4", "", false, false),
        UserData(0, "user5", "user5@gmail.com",  "12345",  ByteArray(0), "User 5", "", false, false)
    )

    init {
        val userDao = AppDatabase.getInstance(application).userDAO
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

    fun getRowCount(callback: (Int) -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getRowCount()
            }
            callback(result)
        }
    }

    fun insertSampleData(){
        viewModelScope.launch(Dispatchers.IO){
            for(user in users){
                repository.addUser(user)
            }
        }
    }



}