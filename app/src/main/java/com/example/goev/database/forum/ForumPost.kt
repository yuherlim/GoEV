package com.example.goev.database.forum

import com.example.goev.database.user.UserData

data class ForumPost(
    var forumPostData: ForumPostData,
    var userData: UserData,
    var noOfLike:  Int,
    var noOfDislike:  Int,
    var noOfComment:Int,
    var hashtagList:  List<ForumHashtagData>?,
    var status: String,
    var userId: Int
    )


