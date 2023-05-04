package com.example.goev.databases.tempUser

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDAO: UserDAO) {
    val readAllUser: LiveData<List<UserData>> = userDAO.getAllUsers()

    fun addUser(userData: UserData){
        userDAO.addUser(userData)
    }

    suspend fun getUserByID(id: Long): UserData{
        return withContext(Dispatchers.IO) {
            userDAO.getUserByID(id)
        }
    }
}