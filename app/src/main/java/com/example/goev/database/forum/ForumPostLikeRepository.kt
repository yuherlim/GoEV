package com.example.goev.database.forum;

import androidx.room.Query

class ForumPostLikeRepository(private val forumPostLikeDao: ForumPostLikeDao) {

    suspend fun addPostLike(forumPostLike: ForumPostLikeData) {
        forumPostLikeDao.addPostLike(forumPostLike)
    }

    suspend fun updatePostLike(forumPostLike: ForumPostLikeData) {
        forumPostLikeDao.updatePostLike(forumPostLike)
    }

    suspend fun deletePostLike(forumPostLike: ForumPostLikeData) {
        forumPostLikeDao.deletePostLike(forumPostLike)
    }

    suspend fun getAllPostLikeList(): List<ForumPostLikeData>? {
        return forumPostLikeDao.getAllPostLikeList()
    }

    suspend fun getPostLikeCount(postId: Int): Int {
        return forumPostLikeDao.getPostLikeCount(postId)
    }

    suspend fun getPostLikeList(userId: String): List<ForumPostLikeData>? {
        return forumPostLikeDao.getPostLikeList(userId)
    }


}

