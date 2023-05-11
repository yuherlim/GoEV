package com.example.goev.databases.react

import androidx.lifecycle.LiveData
import com.example.goev.databases.post.TkPostData

class UserReactRepository(private val react: UserReactDAO, postID:Long) {
    val getAllReactOnAPostData: LiveData<List<UserReactData>> = react.getAllReactsOfAPost(postID)

    fun addReact(reaction: UserReactData){
        react.insertUserReaction(reaction)
    }

}