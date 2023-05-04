package com.example.goev.databases.tempUser

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserData(@ColumnInfo(name="userName")val userName:String){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="userID")var userID: Long = 0L
}
