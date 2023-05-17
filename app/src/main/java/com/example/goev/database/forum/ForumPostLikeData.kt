package com.example.goev.database.forum

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.goev.database.user.UserData

@Entity(
    tableName = "forumPostLike_table",
    foreignKeys = [ForeignKey(
        entity = UserData::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    ), ForeignKey(
        entity = ForumPostData::class,
        parentColumns = ["post_id"],
        childColumns = ["post_id"])]
)
data class ForumPostLikeData(
    @PrimaryKey(autoGenerate = true)
    val postLikeID: Int = 0,

    @ColumnInfo(name = "post_id")
    val postId: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,


    )
