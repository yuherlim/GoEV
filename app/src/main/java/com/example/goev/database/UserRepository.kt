package com.example.goev.database;

import com.example.goev.database.forum.ForumRepliesDao
import com.example.goev.database.forum.ForumRepliesData
import com.example.goev.database.forum.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(userData: UserData) {
        userDao.addUser(userData)
    }

    suspend fun updateUser(userData: UserData) {
        userDao.updateUser(userData)
    }

    suspend fun deleteUser(userData: UserData) {
        userDao.deleteUser(userData)
    }

    suspend fun getUser(userId : String):UserData {
        return userDao.getUser(userId)
    }

    suspend fun getAllUser(): List<UserData>? {
        return userDao.getAllUser()
    }


}

