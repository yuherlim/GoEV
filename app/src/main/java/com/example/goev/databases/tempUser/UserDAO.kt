package com.example.goev.databases.tempUser

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserData)

    @Query("SELECT * FROM user WHERE userID = :id")
    fun getUserByID(id:Long): UserData

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<UserData>>

    @Delete
    suspend fun deleteUser(user:UserData)
}