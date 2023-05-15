package com.example.goev.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserRepository
import com.example.goev.databases.TipsAndKnowledgeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsPrivacyViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = TipsAndKnowledgeDatabase.getInstance(application).userDAO
        repository = UserRepository(userDao)
    }


    fun deleteUser(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteUser()
        }
    }
}

