package com.example.goev.userProfile.userProfile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserRepository
import com.example.goev.databases.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getInstance(application).userDAO
        repository = UserRepository(userDao)
    }

    fun getLoggedInUser(callback: (UserData?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getLoggedInUser()
            callback(result)
        }
    }

    fun updateUserLoggedIn(userId: Int, is_logged_in: Boolean) {
        repository.updateUserLoggedIn(userId, is_logged_in)
    }
}
