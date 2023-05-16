package com.example.goev.database.forum

data class ForumPostComment(
    var forumCommentData: ForumCommentData,
    var RespondedCommentData:ForumCommentData,
    var noOfLike:  Int,
    var noOfDislike:  Int,
    var noOfReplies:Int,
    var status: String,
    var isReply: Boolean,
    var userId: String,
    var parentCommentId : Int

)


