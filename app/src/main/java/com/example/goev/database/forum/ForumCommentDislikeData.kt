package com.example.goev.database.forum

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.goev.database.UserData

@Entity(
    tableName = "forumCommentDislike_table",
    foreignKeys = [ForeignKey(
        entity = UserData::class,
        parentColumns = ["userId"],
        childColumns = ["user_id"]
    ), ForeignKey(
        entity = ForumCommentData::class,
        parentColumns = ["comment_id"],
        childColumns = ["comment_id"])]
)
data class ForumCommentDislikeData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "commentDislike_id")
    var commentDislikeId: Int = 0,

    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "comment_id")
    var commentId: Int,

    @ColumnInfo(name = "created_at")
    var createdAt: Long,


    )
