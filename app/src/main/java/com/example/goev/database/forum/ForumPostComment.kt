package com.example.goev.database.forum

import com.example.goev.database.user.UserData

data class ForumPostComment(
    var forumCommentData: ForumCommentData,
    var respondedCommentData:ForumCommentData,
    var respondentUserData:UserData,
    var userData:UserData,
    var noOfLike: Int,
    var noOfDislike: Int,
    var noOfReplies:Int,
    var status: String,
    var isReply: Boolean,
    var parentCommentId : Int,
    var userName :String

)


