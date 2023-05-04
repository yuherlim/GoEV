package com.example.goev.databases.tempUser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.goev.databases.TipsAndKnowledgeDatabase
import com.example.goev.databases.post.TkPostData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    val readAllUser: LiveData<List<UserData>>
    private val repository: UserRepository

    init{
        val userDAO = TipsAndKnowledgeDatabase.getInstance(application).userDAO
        repository = UserRepository(userDAO)
        readAllUser = repository.readAllUser
    }

    fun addUser(userData: UserData){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(userData)
        }
    }

    suspend fun getUserById(id: Long): UserData {
        return repository.getUserByID(id)
    }
}