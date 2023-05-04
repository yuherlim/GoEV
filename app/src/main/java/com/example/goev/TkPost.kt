package com.example.goev
var tkPostList = mutableListOf<TkPost>()
class TkPost(    val postID: Int = 0,
                 var postTitle: String,
                 var thumbnailID: Int,
                 var contentPreview: String,
                 var totalLikes: Int,
                 var totalDislikes: Int,
                 var totalComments: Int)