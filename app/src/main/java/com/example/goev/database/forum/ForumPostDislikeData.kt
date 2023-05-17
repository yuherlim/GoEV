package com.example.goev.database.forum

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import com.example.goev.database.user.UserData

@Entity(
    tableName = "forumPostDislike_table",
    foreignKeys = [ForeignKey(
        entity = UserData::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    ), ForeignKey(
        entity = ForumPostData::class,
        parentColumns = ["post_id"],
        childColumns = ["post_id"])]
)
data class ForumPostDislikeData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "postDislike_id")
    var postDisikeID: Int = 0,

    @ColumnInfo(name = "post_id")
    var postId: Int,

    @ColumnInfo(name = "user_id")
    var userId: Int,

    @ColumnInfo(name = "created_at")
    var createdAt: Long,


    )
