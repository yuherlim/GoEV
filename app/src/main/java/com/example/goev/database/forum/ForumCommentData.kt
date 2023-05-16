package com.example.goev.database.forum

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.goev.database.UserData

@Entity(
    tableName = "forumComment_table",
    foreignKeys = [ForeignKey(
        entity = UserData::class,
        parentColumns = ["userId"],
        childColumns = ["user_id"]
    ), ForeignKey(
        entity = ForumPostData::class,
        parentColumns = ["post_id"],
        childColumns = ["post_Id"]
    )]
)
data class ForumCommentData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    var commentId: Int = 0,

    @ColumnInfo(name = "post_Id")
    var postId: Int,

    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "created_at")
    var createdAt: Long,

    @ColumnInfo(name = "updated_at")
    var updatedAt: Long

)
