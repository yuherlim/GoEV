package com.example.goev.databases.post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.goev.databases.TipsAndKnowledgeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<TkPostData>>
    private val repository: PostRepository
    private var likes: Int = 0

    init{
        val tkPostDAO = TipsAndKnowledgeDatabase.getInstance(application).postDAO
        repository = PostRepository(tkPostDAO)
        readAllData = repository.readAllData
    }

    fun addPost(ktPostData: TkPostData){
        viewModelScope.launch(Dispatchers.IO){
            repository.addPost(ktPostData)
        }
    }

    private val postDao: TkPostDAO = TipsAndKnowledgeDatabase.getInstance(application).postDAO

    val allPosts: LiveData<List<TkPostData>> = postDao.getAllPosts()

    fun getAPostById(postId: Long): TkPostData {
        return postDao.getAPostById(postId)
    }

    fun getPostById(postId: Long): LiveData<TkPostData> {
        return postDao.getPostById(postId)
    }

    // LiveData for likes and dislikes
    private val _likesCount = MutableLiveData<Int>()
    val likesCount: LiveData<Int> = _likesCount

    private val _dislikesCount = MutableLiveData<Int>()
    val dislikesCount: LiveData<Int> = _dislikesCount

//    fun getCurrentPostLikeCounts(postId: Long){
//        viewModelScope.launch {
//            postDao.getLikesCount(postId)
//        }
//    }
//    fun setCurrentPostLikeCounts(postId: Long){
//    viewModelScope.launch(Dispatchers.IO) {
//          postDao.getLikesCount(postId)
//
//        }
//    }

    fun incrementLikes(postId: Long) {
        viewModelScope.launch {
            postDao.likesCountIncrement(postId)
        }
    }

    fun decrementLikes(postId: Long) {
        viewModelScope.launch {
            postDao.likesCountDecrement(postId)
        }
    }

    fun incrementDislikes(postId: Long) {
        viewModelScope.launch {
            postDao.dislikesCountIncrement(postId)
        }
    }

    fun decrementDislikes(postId: Long) {
        viewModelScope.launch {
            postDao.dislikesCountDecrement(postId)
        }
    }
}