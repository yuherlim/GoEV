package com.example.goev.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserData (
    @PrimaryKey var userId: String,

    @ColumnInfo(name = "name")
    var name: String
)