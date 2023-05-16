package com.example.goev.database.forum;

import androidx.room.Query

class ForumCommentLikeRepository(private val forumCommentLikeDao: ForumCommentLikeDao) {

    suspend fun addCommentLike(forumCommentLike : ForumCommentLikeData) {
        forumCommentLikeDao.addCommentLike(forumCommentLike)
    }

    suspend fun updateCommentLike(forumCommentLike : ForumCommentLikeData) {
        forumCommentLikeDao.updateCommentLike(forumCommentLike)
    }

    suspend fun deleteCommentLike(forumCommentLike : ForumCommentLikeData) {
        forumCommentLikeDao.deleteCommentLike(forumCommentLike)
    }

    suspend fun getAllCommentLikeList(): List<ForumCommentLikeData>? {
        return forumCommentLikeDao.getAllCommentLikeList()
    }

    suspend fun getCommentLikeCount(commentId : Int): Int {
        return forumCommentLikeDao.getCommentLikeCount(commentId)}

    suspend fun getCommentLikeList(userId : String): List<ForumCommentLikeData>? {
            return forumCommentLikeDao.getCommentLikeList(userId)
    }



}

