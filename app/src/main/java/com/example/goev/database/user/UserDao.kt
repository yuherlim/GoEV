package com.example.goev.database.user

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(userdata: UserData)

    @Update
    fun updateUser(userdata: UserData)

    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    fun getUserByEmailAndPassword(email: String, password: String): UserData?

    @Query("SELECT * FROM users WHERE id= :id")
    fun getUserByID(id: Int): UserData?

    // Update a user's profile pic, profile name, phone number, email, and gender by user ID
    @Query("UPDATE users SET profileImage = :newProfilePic, profileName = :newProfileName, phoneNumber = :newPhoneNumber WHERE is_logged_in = 1")
    fun updateUserProfileInfo(newProfilePic: ByteArray, newProfileName: String, newPhoneNumber: String)

    @Query("UPDATE users SET is_logged_in = :is_logged_in WHERE id = :userId")
    fun updateUserLoggedIn(userId: Int, is_logged_in: Boolean)

    // Get the ID of the user who is currently logged in
    @Query("SELECT * FROM users WHERE is_logged_in = 1")
    fun getLoggedInUser(): UserData

    @Query("DELETE FROM users WHERE is_logged_in = 1")
    fun deleteUser()

    //validate the userName exists or not
    @Query("SELECT COUNT(*) FROM users WHERE userName = :userName")
    fun isUserNameExists(userName: String): Boolean

    //validate the userEmail exists or not
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    fun isUserEmailExists(email: String): Boolean

    @Query("UPDATE users SET userName = :newUserName WHERE is_logged_in = 1")
    fun updateUserName(newUserName : String)

    @Query("UPDATE users SET email = :newUserEmail WHERE is_logged_in = 1")
    fun updateUserEmail(newUserEmail : String)

    @Query("UPDATE users SET password = :newUserPassword WHERE is_logged_in = 1")
    fun updateUserPassword(newUserPassword : String)
}