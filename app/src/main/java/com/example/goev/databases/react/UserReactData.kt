package com.example.goev.databases.react

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.goev.databases.post.TkPostData
import com.example.goev.databases.tempUser.UserData

@Entity(tableName="reacts",
primaryKeys=["userID","postID"],
foreignKeys = [ForeignKey(entity = UserData::class, parentColumns = ["userID"], childColumns = ["userID"]),
ForeignKey(entity= TkPostData::class, parentColumns = ["postID"], childColumns = ["postID"])])
data class UserReactData(
    val userID: Long,
    val postID: Long,
    //0 = dislike, 1 = like
    var reaction: Int
){
}
