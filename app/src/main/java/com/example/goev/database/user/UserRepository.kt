package com.example.goev.database.user

import androidx.lifecycle.LiveData




class UserRepository(private val userDao: UserDao) {


    fun addUser(userdata: UserData){
        userDao.addUser(userdata)
    }

    fun getUserByEmailAndPassword(email: String, password: String): UserData? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

}