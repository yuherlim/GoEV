package com.example.goev.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goev.ProfilePicConverter

@Entity(tableName = "users")
@TypeConverters(ProfilePicConverter::class)
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id") val id: Int = 0,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "profileImage") val profileImage: ByteArray?,
    @ColumnInfo(name = "profileName") val profileName : String,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "is_logged_in") val is_logged_in: Boolean,
    @ColumnInfo(name = "is_super") val is_super: Boolean
)


