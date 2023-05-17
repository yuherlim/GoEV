package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForumHashtagDao {
    @Insert
    suspend fun addHashTag(forumHashtagData: ForumHashtagData)

    @Update
    suspend fun updateHashTag(forumHashtagData: ForumHashtagData)

    @Delete
    suspend fun deleteHashTag(forumHashtagData: ForumHashtagData)

    @Query("SELECT * FROM forumHashtag_table")
    suspend fun getHashTagList(): List<ForumHashtagData>?




}