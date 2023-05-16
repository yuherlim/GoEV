package com.example.goev.database.forum

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.goev.database.UserData

@Entity(
    tableName = "forumReplies_table",
    foreignKeys = [ForeignKey(
        entity = ForumCommentData::class,
        parentColumns = ["comment_id"],
        childColumns = ["initial_commentId"]
    ), ForeignKey(
        entity = ForumCommentData::class,
        parentColumns = ["comment_id"],
        childColumns = ["responded_commentId"]
    ), ForeignKey(
            entity = ForumCommentData::class,
            parentColumns = ["comment_id"],
            childColumns = ["parent_commmentId"]
        )]
)
data class ForumRepliesData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "replies_id")
    var repliesID: Int = 0,

    @ColumnInfo(name = "parent_commmentId")
    var parentCommmentId: Int,

    @ColumnInfo(name = "initial_commentId")
    var initialCommentID: Int,

    @ColumnInfo(name = "responded_commentId")
    var respondedCommentID: Int,

    )
