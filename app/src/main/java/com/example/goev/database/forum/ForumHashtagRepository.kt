package com.example.goev.database.forum;

class ForumHashtagRepository(private val forumHashtagDao: ForumHashtagDao) {

    suspend fun addHashTag(forumHashtagData: ForumHashtagData) {
        forumHashtagDao.addHashTag(forumHashtagData)
    }

    suspend fun updateHashTag(forumHashtagData: ForumHashtagData) {
        forumHashtagDao.updateHashTag(forumHashtagData)
    }

    suspend fun deleteHashTag(forumHashtagData: ForumHashtagData) {
        forumHashtagDao.deleteHashTag(forumHashtagData)
    }

    suspend fun getHashTagList(): List<ForumHashtagData>? {
        return forumHashtagDao.getHashTagList()
    }

}

