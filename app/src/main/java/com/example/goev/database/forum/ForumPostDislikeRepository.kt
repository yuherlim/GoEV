package com.example.goev.database.forum;

import androidx.room.Query

class ForumPostDislikeRepository(private val forumPostDislikeDao: ForumPostDislikeDao) {

    suspend fun addPostDislike(forumPostDislike : ForumPostDislikeData) {
        forumPostDislikeDao.addPostDislike(forumPostDislike)
    }

    suspend fun updatePostDislike(forumPostDislike : ForumPostDislikeData) {
        forumPostDislikeDao.updatePostDislike(forumPostDislike)
    }

    suspend fun deletePostDislike(forumPostDislike : ForumPostDislikeData) {
        forumPostDislikeDao.deletePostDislike(forumPostDislike)
    }

    suspend fun getAllPostDislikeList(): List<ForumPostDislikeData>? {
        return forumPostDislikeDao.getAllPostDislikeList()
    }

    suspend fun getPostDislikeCount(postId : Int): Int {
        return forumPostDislikeDao.getPostDislikeCount(postId)}

    suspend fun getPostDislikeList(userId : Int): List<ForumPostDislikeData>? {
            return forumPostDislikeDao.getPostDislikeList(userId)
    }

}

