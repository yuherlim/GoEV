package com.example.goev.databases.postcomment

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.example.goev.DateConverters
import com.example.goev.TkImageConverter
import com.example.goev.databases.post.TkPostData
import com.example.goev.databases.tempUser.UserData
import java.util.*

@Entity(tableName="comments",
    foreignKeys = [ForeignKey(entity = TkPostData::class,
        parentColumns = ["postID"],
        childColumns = ["postID"],
        onDelete = CASCADE),
        ForeignKey(entity = UserData::class,
        parentColumns = ["userID"],
        childColumns = ["userID"],
        onDelete = CASCADE)])
@TypeConverters(TkImageConverter::class, DateConverters::class)
data class TkPostCommentData(
    @ColumnInfo(name="userID")val userId:Long,
    @ColumnInfo(name="userName")val userName: String,
    @ColumnInfo(name="comment")val comment: String,
    @ColumnInfo(name="postID")val postId: Long
) {
    @PrimaryKey(autoGenerate = true)  var id: Long = 0L
    @ColumnInfo(name="commentTime") var commentTime: Date = Date()
    @ColumnInfo(name="totalLikes")var totalLikes: Int = 0
    @ColumnInfo(name="totalDislikes")var totalDislikes: Int = 0
}
