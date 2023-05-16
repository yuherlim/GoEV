package com.example.goev.database.forum

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.goev.database.UserData

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(userData: UserData)

    @Update
    suspend fun updateUser(userData: UserData)

    @Delete()
    suspend fun deleteUser(userData: UserData)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUser(): List<UserData>?

    @Query("SELECT * FROM user_table WHERE userId = :userId")
    suspend fun getUser(userId : String): UserData




}