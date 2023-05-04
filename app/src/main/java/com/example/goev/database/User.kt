package com.example.goev.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "passwords")
    val password: String?
    )
