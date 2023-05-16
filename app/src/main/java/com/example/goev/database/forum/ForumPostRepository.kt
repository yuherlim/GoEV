package com.example.goev.database.forum;

class ForumPostRepository(private val forumPostDao: ForumPostDao) {

    suspend fun addPost(forumPostData: ForumPostData) {
        forumPostDao.addPost(forumPostData)
    }

    suspend fun getAllPost(): List<ForumPostData>? {
        return forumPostDao.getAllPost()
    }

    suspend fun getPost(postId : Int): ForumPostData {
        return forumPostDao.getPost(postId)
    }

    suspend fun updatePost(forumPostData: ForumPostData) {
        forumPostDao.updatePost(forumPostData)
    }

    suspend fun deletePost(forumPostData: ForumPostData) {
        forumPostDao.deletePost(forumPostData)
    }


}

