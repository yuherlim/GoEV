package com.example.goev.databases.postcomment

import androidx.lifecycle.LiveData
import androidx.room.*
import org.w3c.dom.Comment

@Dao
interface TkPostCommentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addComment(comment: TkPostCommentData)

    @Query("SELECT * FROM comments WHERE id = :id")
    fun getCommentByID(id:String): TkPostCommentData?

    @Query("SELECT * FROM comments")
    fun getAllComments(): LiveData<List<TkPostCommentData>>

    @Query("SELECT * FROM comments WHERE postID = :postId")
    fun getCommentsForPost(postId: Long): LiveData<List<TkPostCommentData>>

    @Update
    fun editComment(comment: TkPostCommentData)

    @Delete
    suspend fun deleteComment(comment: TkPostCommentData)
}