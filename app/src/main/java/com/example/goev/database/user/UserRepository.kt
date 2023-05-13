package com.example.goev.database.user

import androidx.lifecycle.LiveData




class UserRepository(private val userDao: UserDao) {


    fun addUser(userdata: UserData){
        userDao.addUser(userdata)
    }

    fun updateUser(userdata: UserData){
        userDao.updateUser(userdata)
    }

    fun getUserByEmailAndPassword(email: String, password: String): UserData? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    fun updateUserProfileInfo(userId: Int, newProfileName: String, newPhoneNumber: String, newEmail: String, newGender: String){
        userDao.updateUserProfileInfo(userId, newProfileName, newPhoneNumber, newEmail, newGender)
    }

    fun updateUserLoggedIn(userId: Int, is_logged_in: Boolean){
        userDao.updateUserLoggedIn(userId, is_logged_in)
    }

    fun getLoggedInUser(): UserData?{
        return userDao.getLoggedInUser()
    }

    fun deleteUser(){
        userDao.deleteUser()
    }

    fun isUserNameExists(userName: String): Boolean{
        return userDao.isUserNameExists(userName)
    }

    fun isUserEmailExists(email: String): Boolean{
        return userDao.isUserEmailExists(email)
    }

    fun updateUserName(newUserName : String){
        userDao.updateUserName(newUserName)
    }

    fun updateUserEmail(newUserEmail : String){
        userDao.updateUserEmail(newUserEmail)
    }

    fun updateUserPassword(newUserPassword : String){
        userDao.updateUserPassword(newUserPassword)
    }
}