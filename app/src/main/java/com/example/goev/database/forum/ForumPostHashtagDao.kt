package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForumPostHashtagDao {
    @Insert
    suspend fun addPostHashtag(forumPostHashtagData: ForumPostHashtagData)

    @Update
    suspend fun updatePostHashtag(forumPostHashtagData: ForumPostHashtagData)

    @Delete
    suspend fun deletePostHashtag(forumPostHashtagData: ForumPostHashtagData)

    @Query("SELECT * FROM forumPostHashtag_table")
    suspend fun getAllPostHashtagList(): List<ForumPostHashtagData>?

    @Query("SELECT * FROM forumPostHashtag_table WHERE post_id = :postId")
    suspend fun getPostHashtagList(postId : String): List<ForumPostHashtagData>?




}