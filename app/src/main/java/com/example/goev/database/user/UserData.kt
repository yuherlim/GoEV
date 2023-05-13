package com.example.goev.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id") var id: Int = 0,
    @ColumnInfo(name = "userName") var userName: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "profileImageUrl") var profileImageUrl: String,
    @ColumnInfo(name = "profileName") var profileName : String,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String,
    @ColumnInfo(name = "is_logged_in") var is_logged_in: Boolean,
    @ColumnInfo(name = "is_super") var is_super: Boolean
)


