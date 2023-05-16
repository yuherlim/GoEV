package com.example.goev.database.forum;

class ForumRepliesRepository(private val forumRepliesDao: ForumRepliesDao) {

    suspend fun addReplies(forumRepliesData: ForumRepliesData) {
        forumRepliesDao.addReplies(forumRepliesData)
    }

    suspend fun updateReplies(forumRepliesData: ForumRepliesData) {
        forumRepliesDao.updateReplies(forumRepliesData)
    }

    suspend fun deleteReplies(forumRepliesData: ForumRepliesData) {
        forumRepliesDao.deleteReplies(forumRepliesData)
    }

    suspend fun getAllReplies(): List<ForumRepliesData>? {
        return forumRepliesDao.getAllReplies()
    }

    suspend fun getRepliesList(commentId :Int): List<ForumRepliesData>? {
        return forumRepliesDao.getRepliesList(commentId)
    }


}

