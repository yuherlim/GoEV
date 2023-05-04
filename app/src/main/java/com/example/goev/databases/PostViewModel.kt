package com.example.goev.databases

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<TkPostData>>
    private val repository: PostRepository

    init{
        val tkPostDAO = TkPostDatabase.getInstance(application).tkPostDAO()
        repository = PostRepository(tkPostDAO)
        readAllData = repository.readAllData
    }

    fun addPost(ktPostData: TkPostData){
        viewModelScope.launch(Dispatchers.IO){
            repository.addPost(ktPostData)
        }
    }
}