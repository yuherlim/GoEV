package com.example.goev.databases.postcomment

import androidx.lifecycle.LiveData

class PostCommentRepository(private val comment: TkPostCommentDAO) {
    val readAllPostData: LiveData<List<TkPostCommentData>> = comment.getAllComments()

    fun addComment(tkPostComment: TkPostCommentData){
        comment.addComment(tkPostComment)
    }

}