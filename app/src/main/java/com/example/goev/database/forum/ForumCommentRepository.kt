package com.example.goev.database.forum;

class ForumCommentRepository(private val forumCommentDao: ForumCommentDao) {

    suspend fun addComment(forumCommentData: ForumCommentData) {
        forumCommentDao.addComment(forumCommentData)
    }

    suspend fun updateComment(forumCommentData: ForumCommentData) {
        forumCommentDao.updateComment(forumCommentData)
    }

    suspend fun deleteComment(forumCommentData: ForumCommentData) {
        forumCommentDao.deleteComment(forumCommentData)
    }

    suspend fun getAllComment(): List<ForumCommentData>? {
        return forumCommentDao.getAllComment()
    }

    suspend fun getCommentList(postId : Int): List<ForumCommentData>? {
        return forumCommentDao.getCommentList(postId)
    }

}

