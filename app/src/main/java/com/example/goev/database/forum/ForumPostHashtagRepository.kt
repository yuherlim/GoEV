package com.example.goev.database.forum;

class ForumPostHashtagRepository(private val forumPostHashtagDao: ForumPostHashtagDao) {

    suspend fun addPostHashtag(forumPostHashtagData: ForumPostHashtagData) {
        forumPostHashtagDao.addPostHashtag(forumPostHashtagData)
    }

    suspend fun updatePostHashtag(forumPostHashtagData: ForumPostHashtagData) {
        forumPostHashtagDao.updatePostHashtag(forumPostHashtagData)
    }

    suspend fun deletePostHashtag(forumPostHashtagData: ForumPostHashtagData) {
        forumPostHashtagDao.deletePostHashtag(forumPostHashtagData)
    }

    suspend fun getAllPostHashtagList(): List<ForumPostHashtagData>? {
        return forumPostHashtagDao.getAllPostHashtagList()
    }

    suspend fun getPostHashtagList(postId : String): List<ForumPostHashtagData>? {
        return forumPostHashtagDao.getPostHashtagList(postId)
    }

}

