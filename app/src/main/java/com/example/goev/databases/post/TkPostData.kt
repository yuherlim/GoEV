package com.example.goev.databases.post

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.goev.DateConverters
import com.example.goev.TkImageConverter
import java.util.*


//@ColumnInfo(typeAffinity = ColumnInfo.BLOB) val thumbnail: ByteArray,
//@Entity(tableName = "posts")
@Entity(tableName = "posts")
@TypeConverters(TkImageConverter::class, DateConverters::class)
data class TkPostData(@ColumnInfo(name="title")val title: String,
                      @ColumnInfo(name="thumbnail")val thumbnail:ByteArray?,
                      @ColumnInfo(name="content")val content: String){
    //values that shouldn't be affect by the constructor
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="postID") var id: Long = 0L
    @ColumnInfo(name="date")var postDate: Date = Date()
    @ColumnInfo(name="likes")var totalLikes: Int = 0
    @ColumnInfo(name="dislikes")var totalDislikes: Int = 0
    @ColumnInfo(name="totalComments")var totalComments: Int = 0
}

