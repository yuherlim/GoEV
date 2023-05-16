package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForumPostDislikeDao {
    @Insert
    suspend fun addPostDislike(forumPostDislike : ForumPostDislikeData)

    @Update
    suspend fun updatePostDislike(forumPostDislike : ForumPostDislikeData)

    @Delete()
    suspend fun deletePostDislike(forumPostDislike : ForumPostDislikeData)

    @Query("SELECT * FROM forumPostDislike_table")
    suspend fun getAllPostDislikeList(): List<ForumPostDislikeData>?

    @Query("SELECT COUNT(*) FROM forumPostDislike_table WHERE post_id = :postId")
    suspend fun getPostDislikeCount(postId : Int): Int

    @Query("SELECT * FROM forumPostDislike_table WHERE user_id = :userId")
    suspend fun getPostDislikeList(userId : String): List<ForumPostDislikeData>?



}