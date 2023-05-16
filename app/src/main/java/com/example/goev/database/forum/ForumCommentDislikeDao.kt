package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForumCommentDislikeDao {
    @Insert
    suspend fun addCommentDislike(forumCommentDislike : ForumCommentDislikeData)

    @Update
    suspend fun updateCommentDislike(forumCommentDislike : ForumCommentDislikeData)

    @Delete()
    suspend fun deleteCommentDislike(forumCommentDislike : ForumCommentDislikeData)

    @Query("SELECT * FROM forumCommentDislike_table")
    suspend fun getAllCommentDislikeList(): List<ForumCommentDislikeData>?

    @Query("SELECT COUNT(*) FROM forumCommentDislike_table WHERE comment_id = :commentId")
    suspend fun getCommentDislikeCount(commentId : Int): Int

    @Query("SELECT * FROM forumCommentDislike_table WHERE user_id = :userId")
    suspend fun getCommentDislikeList(userId : String): List<ForumCommentDislikeData>?




}