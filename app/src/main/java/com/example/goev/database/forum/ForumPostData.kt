package com.example.goev.database.forum

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import com.example.goev.database.user.UserData

@Entity(
    tableName = "forumPost_table",
    foreignKeys = [ForeignKey(
        entity = UserData::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    )]
)
data class ForumPostData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    var postId: Int = 0,

    @ColumnInfo(name = "user_id")
    var userId: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "created_at")
    var createdAt: Long,

    @ColumnInfo(name = "updated_at")
    var updatedAt: Long


    )
