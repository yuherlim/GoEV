package com.example.goev.database.forum

data class ForumPost(
    var forumPostData: ForumPostData,
    var noOfLike:  Int,
    var noOfDislike:  Int,
    var noOfComment:Int,
    var hashtagList:  List<ForumHashtagData>?,
    var status: String,
    var userId: String
    )


