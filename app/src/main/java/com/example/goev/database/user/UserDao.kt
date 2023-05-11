package com.example.goev.database.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(userdata: UserData)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): UserData?

    @Delete
    fun deleteUser(userdata: UserData)

    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    fun getUserByEmailAndPassword(email: String, password: String): UserData?

}