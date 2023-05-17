package com.example.goev

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.example.goev.databases.AppDatabase
import com.example.goev.databases.post.TkPostData
import com.example.goev.databases.react.UserReactDAO
import com.example.goev.databases.react.UserReactData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TkPostContentViewModel(application:Application): AndroidViewModel(application) {
    val postUpdateLiveData = MutableLiveData<TkPostData>()
    val userReactLiveData = MutableLiveData<UserReactData?>()
    val tkPostDAO = AppDatabase.getInstance(application).postDAO
//    val loggedInUser = MutableLiveData<UserData>()
    private val userReactDao: UserReactDAO = AppDatabase.getInstance(application).userReactDAO
//    private val userDao: UserDao = UserDatabase.getInstance(application).userDao()


    fun setSpecificPost(postID: Long){
        viewModelScope.launch(Dispatchers.IO){
            val postUpdate = tkPostDAO.getAPostById(postID)
            postUpdateLiveData.postValue(postUpdate)
        }
    }

    fun userReactionPreviously(userID: Int, postID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val userReact = userReactDao.getAUserReactOnAPost(userID, postID)
            userReactLiveData.postValue(userReact)
        }
    }

    // function to increase likes by 1
    fun increaseLikes() {
        val postUpdate = postUpdateLiveData.value
        if (postUpdate != null) {
            postUpdate.totalLikes += 1
            postUpdateLiveData.postValue(postUpdate)
        }
    }

    // function to decrease likes by 1
    fun decreaseLikes() {
        val postUpdate = postUpdateLiveData.value
        if (postUpdate != null) {
            postUpdate.totalLikes -= 1
            postUpdateLiveData.postValue(postUpdate)
        }
    }

    // function to increase dislikes by 1
    fun increaseDislikes() {
        val postUpdate = postUpdateLiveData.value
        if (postUpdate != null) {
            postUpdate.totalDislikes += 1
            postUpdateLiveData.postValue(postUpdate)
        }
    }

    // function to decrease dislikes by 1
    fun decreaseDislikes() {
        val postUpdate = postUpdateLiveData.value
        if (postUpdate != null) {
            postUpdate.totalDislikes -= 1
            postUpdateLiveData.postValue(postUpdate)
        }
    }

//    fun increaseComment(){
//        val postUpdate = postUpdateLiveData.value
//        if (postUpdate != null) {
//            postUpdate.totalComments += 1
//            postUpdateLiveData.postValue(postUpdate)
//        }
//    }
//
//    fun decreaseComment(){
//        val postUpdate = postUpdateLiveData.value
//        if (postUpdate != null) {
//            postUpdate.totalComments -= 1
//            postUpdateLiveData.postValue(postUpdate)
//        }
//    }

    fun deletePost(postID: Long){
        viewModelScope.launch(Dispatchers.IO){
            tkPostDAO.deletePost(tkPostDAO.getAPostById(postID))
        }
    }

    fun confirmedModifyPost(postID: Long, title: String, content: String){
        viewModelScope.launch(Dispatchers.IO){
            tkPostDAO.updatePostTitleAndContent(postID,title,content)
        }
    }

    fun sharePost(context: Context, title: String, content: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, content)
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }


//    fun getCurrentUser(){
//            viewModelScope.launch(Dispatchers.IO){
//                loggedInUser.postValue(userDao.getLoggedInUser())
//            }
//    }
}