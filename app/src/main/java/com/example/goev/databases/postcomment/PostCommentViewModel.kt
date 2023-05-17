package com.example.goev.databases.postcomment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.goev.databases.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostCommentViewModel(application: Application): AndroidViewModel(application) {
    val readAllPostData: LiveData<List<TkPostCommentData>>
    private val repository: PostCommentRepository

    init{
        val tkPostCommentDao = AppDatabase.getInstance(application).postCommentDAO
        repository = PostCommentRepository(tkPostCommentDao)
        readAllPostData = repository.readAllPostData
    }

    fun addComment(tkPostComment: TkPostCommentData){
        viewModelScope.launch(Dispatchers.IO){
            repository.addComment(tkPostComment)
    }
    }
}