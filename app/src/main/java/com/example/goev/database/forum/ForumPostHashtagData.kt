package com.example.goev.database.forum

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "forumPostHashtag_table",
    foreignKeys = [ForeignKey(
        entity = ForumPostData::class,
        parentColumns = ["post_id"],
        childColumns = ["post_id"]
    ), ForeignKey(
        entity = ForumHashtagData::class,
        parentColumns = ["hashtagId"],
        childColumns = ["hashtag_id"])])
data class ForumPostHashtagData(
    @PrimaryKey var postHashtagId: String,

    @ColumnInfo(name = "hashtag_id")
    var hashtagId: String,

    @ColumnInfo(name = "post_id")
    var postId: Int
)
