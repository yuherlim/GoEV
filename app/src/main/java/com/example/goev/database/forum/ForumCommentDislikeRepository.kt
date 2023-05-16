package com.example.goev.database.forum;

import androidx.room.Query

class ForumCommentDislikeRepository(private val forumCommentDislikeDao: ForumCommentDislikeDao) {

    suspend fun addCommentDislike(forumCommentDislike : ForumCommentDislikeData) {
        forumCommentDislikeDao.addCommentDislike(forumCommentDislike)
    }

    suspend fun updateCommentDislike(forumCommentDislike : ForumCommentDislikeData) {
        forumCommentDislikeDao.updateCommentDislike(forumCommentDislike)
    }

    suspend fun deleteCommentDislike(forumCommentDislike : ForumCommentDislikeData) {
        forumCommentDislikeDao.deleteCommentDislike(forumCommentDislike)
    }

    suspend fun getAllCommentDislikeList(): List<ForumCommentDislikeData>? {
        return forumCommentDislikeDao.getAllCommentDislikeList()
    }

    suspend fun getCommentDislikeCount(commentId : Int): Int {
        return forumCommentDislikeDao.getCommentDislikeCount(commentId)}

    suspend fun getCommentDislikeList(userId : String): List<ForumCommentDislikeData>? {
            return forumCommentDislikeDao.getCommentDislikeList(userId)
    }




}

