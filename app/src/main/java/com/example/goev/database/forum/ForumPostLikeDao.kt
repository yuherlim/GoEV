package com.example.goev.database.forum

import androidx.room.*

@Dao
interface ForumPostLikeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPostLike(forumPostLike : ForumPostLikeData)

    @Update
    suspend fun updatePostLike(forumPostLike : ForumPostLikeData)

    @Delete
    suspend fun deletePostLike(forumPostLike : ForumPostLikeData)

    @Query("SELECT * FROM forumPostLike_table")
    suspend fun getAllPostLikeList(): List<ForumPostLikeData>?

    @Query("SELECT COUNT(*) FROM forumPostLike_table WHERE post_id = :postId")
    suspend fun getPostLikeCount(postId : Int): Int

    @Query("SELECT * FROM forumPostLike_table WHERE user_id = :userId")
    suspend fun getPostLikeList(userId : Int): List<ForumPostLikeData>?



}