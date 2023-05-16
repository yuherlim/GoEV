package com.example.goev.database.forum

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forumHashtag_table")
data class ForumHashtagData (
    @PrimaryKey var hashtagId: String,

    @ColumnInfo(name = "hashtag")
    var hashtag: String
)
