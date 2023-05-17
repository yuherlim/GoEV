package com.example.goev.database.user


class UserRepository(private val userDao: UserDao) {


    fun addUser(userdata: UserData) {
        userDao.addUser(userdata)
    }

    fun updateAllUsersLoggedOut() {
        userDao.updateAllUsersLoggedOut()
    }

    fun getUserByEmailAndPassword(email: String, password: String): UserData? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    fun updateUserProfileInfo(
        newProfilePic: ByteArray,
        newProfileName: String,
        newPhoneNumber: String
    ) {
        userDao.updateUserProfileInfo(newProfilePic, newProfileName, newPhoneNumber)
    }

    fun updateUserLoggedIn(userId: Int, is_logged_in: Boolean) {
        userDao.updateUserLoggedIn(userId, is_logged_in)
    }

    fun getLoggedInUser(): UserData? {
        return userDao.getLoggedInUser()
    }

    fun deleteUser() {
        userDao.deleteUser()
    }

    fun isUserNameExists(userName: String): Boolean {
        return userDao.isUserNameExists(userName)
    }

    fun isUserEmailExists(email: String): Boolean {
        return userDao.isUserEmailExists(email)
    }

    fun updateUserName(newUserName: String) {
        userDao.updateUserName(newUserName)
    }

    fun updateUserEmail(newUserEmail: String) {
        userDao.updateUserEmail(newUserEmail)
    }

    fun updateUserPassword(newUserPassword: String) {
        userDao.updateUserPassword(newUserPassword)
    }

    fun getRowCount(): Int {
        return userDao.getRowCount()
    }

    fun getAllUsers(): List<UserData>{
        return userDao.getAllUsers()

    }
}