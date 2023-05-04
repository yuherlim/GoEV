package com.example.goev.databases.post

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
public interface TkPostDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPost(post: TkPostData)

    @Query("SELECT * FROM posts ORDER BY date DESC")
    fun getAllPosts(): LiveData<List<TkPostData>>

    @Query("SELECT * FROM posts WHERE postID = :postId")
    fun getPostById(postId: Int): LiveData<TkPostData>

    @Query("SELECT totalLikes, totalDislikes FROM posts WHERE postID = :postId")
    fun getLikesAndDislikes(postId: Long): LiveData<Pair<Int, Int>>

    //if user made new comment
    @Query("UPDATE posts SET totalComments = totalComments+1 WHERE postID = :postId")
    suspend fun commentCountIncrement(postId:Long)

    //if user delete the comment made
    @Query("UPDATE posts SET totalComments = totalComments-1 WHERE postID = :postId")
    suspend fun commentCountDecrement(postId:Long)

//    @Query("UPDATE posts SET likes = likes-1 WHERE postID = :postId")
//    suspend fun likesCountDecrement(postId:Long)
//
//    @Query("UPDATE posts SET likes = likes+1 WHERE postID = :postId")
//    suspend fun likesCountIncrement(postId:Long)
//
//    @Query("UPDATE posts SET dislikes = dislikes-1 WHERE postID = :postId")
//    suspend fun dislikesCountDecrement(postId:Long)
//
//    @Query("UPDATE posts SET dislikes = dislikes+1 WHERE postID = :postId")
//    suspend fun dislikesCountIncrement(postId:Long)

    @Delete
    suspend fun deletePost(post: TkPostData)
}