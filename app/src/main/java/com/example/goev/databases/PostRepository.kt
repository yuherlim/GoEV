package com.example.goev.databases

import androidx.lifecycle.LiveData
import com.example.goev.databases.TkPostDAO
import com.example.goev.databases.TkPostData

class PostRepository(private val tkPostDao: TkPostDAO) {
    val readAllData: LiveData<List<TkPostData>> = tkPostDao.getAllPosts()

    fun addPost(tkPostData: TkPostData){
        tkPostDao.addPost(tkPostData)
    }

}