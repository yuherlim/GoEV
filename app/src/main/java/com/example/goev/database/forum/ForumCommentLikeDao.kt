package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForumCommentLikeDao {
    @Insert
    suspend fun addCommentLike(forumCommentLike : ForumCommentLikeData)

    @Update
    suspend fun updateCommentLike(forumCommentLike : ForumCommentLikeData)

    @Delete()
    suspend fun deleteCommentLike(forumCommentLike : ForumCommentLikeData)

    @Query("SELECT * FROM forumCommentLike_table")
    suspend fun getAllCommentLikeList(): List<ForumCommentLikeData>?

    @Query("SELECT COUNT(*) FROM forumCommentLike_table WHERE comment_id = :commentId")
    suspend fun getCommentLikeCount(commentId : Int): Int

    @Query("SELECT * FROM forumCommentLike_table WHERE user_id = :userId")
    suspend fun getCommentLikeList(userId : String): List<ForumCommentLikeData>?




}