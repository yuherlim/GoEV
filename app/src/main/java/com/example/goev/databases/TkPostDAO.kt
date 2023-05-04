package com.example.goev.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
public interface TkPostDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPost(post: TkPostData)

    @Query("SELECT * FROM test_posts ORDER BY date DESC")
    fun getAllPosts(): LiveData<List<TkPostData>>

    @Query("SELECT * FROM test_posts WHERE id = :postId")
    fun getPostById(postId: Int): LiveData<TkPostData>
}