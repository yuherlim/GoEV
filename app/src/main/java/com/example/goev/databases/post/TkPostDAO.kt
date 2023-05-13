package com.example.goev.databases.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
public interface TkPostDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPost(post: TkPostData)

    @Query("SELECT * FROM posts ORDER BY date DESC")
    fun getAllPosts(): LiveData<List<TkPostData>>

    @Query("SELECT * FROM posts WHERE postID = :postId")
    fun getPostById(postId: Long): LiveData<TkPostData>

    @Query("SELECT * FROM posts WHERE postID = :postId")
    fun getAPostById(postId: Long): TkPostData

    @Query("SELECT likes FROM posts WHERE postID = :postId")
    fun getLikesCount(postId: Long): Int

    //if user made new comment
    @Query("UPDATE posts SET totalComments = totalComments+1 WHERE postID = :postId")
    suspend fun commentCountIncrement(postId:Long)

    //if user delete the comment made
    @Query("UPDATE posts SET totalComments = totalComments-1 WHERE postID = :postId")
    suspend fun commentCountDecrement(postId:Long)

    @Query("UPDATE posts SET likes = likes-1 WHERE postID = :postId")
    suspend fun likesCountDecrement(postId:Long)

    @Query("UPDATE posts SET likes = likes+1 WHERE postID = :postId")
    suspend fun likesCountIncrement(postId:Long)

    @Query("UPDATE posts SET dislikes = dislikes-1 WHERE postID = :postId")
    suspend fun dislikesCountDecrement(postId:Long)

    @Query("UPDATE posts SET dislikes = dislikes+1 WHERE postID = :postId")
    suspend fun dislikesCountIncrement(postId:Long)

    @Delete
    suspend fun deletePost(post: TkPostData)

    @Query("UPDATE posts SET title = :title, content = :content WHERE postID = :postId")
    suspend fun updatePostTitleAndContent(postId: Long, title: String, content: String)

    @Query("SELECT * FROM posts WHERE LOWER(title) LIKE LOWER(:searchQuery) OR LOWER(content) LIKE LOWER(:searchQuery) ORDER BY date DESC")
    fun searchPosts(searchQuery: String): List<TkPostData>
}