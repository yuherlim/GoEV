package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForumPostDao {
    @Insert
    suspend fun addPost(forumPostData: ForumPostData)

    @Update
    suspend fun updatePost(forumPostData: ForumPostData)

    @Delete()
    suspend fun deletePost(forumPostData: ForumPostData)

    @Query("SELECT * FROM forumPost_table")
    suspend fun getAllPost(): List<ForumPostData>?

    @Query("SELECT * FROM forumPost_table WHERE post_id = :postId")
    suspend fun getPost(postId: Int): ForumPostData



}