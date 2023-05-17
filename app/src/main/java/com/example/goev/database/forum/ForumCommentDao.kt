package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForumCommentDao {
    @Insert
    suspend fun addComment(forumCommentData: ForumCommentData)

    @Update
    suspend fun updateComment(forumCommentData: ForumCommentData)

    @Delete
    suspend fun deleteComment(forumCommentData: ForumCommentData)

    @Query("SELECT * FROM forumComment_table")
    suspend fun getAllComment(): List<ForumCommentData>?

    @Query("SELECT * FROM forumComment_table WHERE post_id = :postId")
    suspend fun getCommentList(postId : Int): List<ForumCommentData>?





}