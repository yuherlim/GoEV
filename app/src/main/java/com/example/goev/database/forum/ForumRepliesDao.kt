package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForumRepliesDao {
    @Insert
    suspend fun addReplies(forumRepliesData: ForumRepliesData)

    @Update
    suspend fun updateReplies(forumRepliesData: ForumRepliesData)

    @Delete
    suspend fun deleteReplies(forumRepliesData: ForumRepliesData)

    @Query("SELECT * FROM forumReplies_table")
    suspend fun getAllReplies(): List<ForumRepliesData>?

    @Query("SELECT * FROM forumReplies_table WHERE initial_commentId = :commentId")
    suspend fun getRepliesList(commentId :Int): List<ForumRepliesData>?




}