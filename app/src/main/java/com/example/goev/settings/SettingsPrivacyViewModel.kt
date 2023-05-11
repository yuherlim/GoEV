package com.example.goev.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserDatabase
import com.example.goev.database.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsPrivacyViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun deleteUser(userData: UserData){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.deleteUser(userData)
        }
    }

    fun getUserById(id: Int){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.getUserById(id)
        }
    }

}