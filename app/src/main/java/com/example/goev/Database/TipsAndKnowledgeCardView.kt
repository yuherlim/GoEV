package com.example.goev.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "each_card_view")
data class TipsAndKnowledgeCardView(
    @PrimaryKey(autoGenerate = true)
    val postID: Long,

    @ColumnInfo(name="date_posted")
    val datePosted: Long,

    @ColumnInfo(name="title")
    val title: String,

    @ColumnInfo(name="thumbnail_ID")
    val thumbnailID: Long,

    @ColumnInfo(name="content")
    val content: String
    )

