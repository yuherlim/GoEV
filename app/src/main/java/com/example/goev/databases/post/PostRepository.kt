package com.example.goev.databases.post

import androidx.lifecycle.LiveData

class PostRepository(private val tkPostDao: TkPostDAO) {
    val readAllData: LiveData<List<TkPostData>> = tkPostDao.getAllPosts()

    fun addPost(tkPostData: TkPostData){
        tkPostDao.addPost(tkPostData)
    }

}