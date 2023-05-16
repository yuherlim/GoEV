package com.example.goev.databases.react

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.goev.databases.TipsAndKnowledgeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserReactViewModel(application: Application, postID: Long): AndroidViewModel(application) {
    val readPostAllReact: LiveData<List<UserReactData>>
    private val repository: UserReactRepository

    init{
        val userReactDAO = TipsAndKnowledgeDatabase.getInstance(application).userReactDAO
        repository = UserReactRepository(userReactDAO, postID)
        readPostAllReact = repository.getAllReactOnAPostData
    }

    fun addReact(react: UserReactData){
        viewModelScope.launch(Dispatchers.IO){
            repository.addReact(react)
        }
    }
}