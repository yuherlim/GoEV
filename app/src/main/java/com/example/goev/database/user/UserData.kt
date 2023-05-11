package com.example.goev.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "ID") var id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String
)


