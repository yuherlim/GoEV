package com.example.goev

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.goev.databases.AppDatabase
import com.example.goev.databases.post.PostViewModel
import com.example.goev.databases.react.UserReactDAO
import com.example.goev.databases.react.UserReactData
import com.example.goev.databinding.ActivityTkPostContentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TkPostContent : AppCompatActivity() {
    lateinit var binding: ActivityTkPostContentBinding
    lateinit var postContentVM: TkPostContentViewModel
    lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tk_post_content)

        //if the value for postID found, the value will be passed, else the value will be 0
        val postID = intent.getLongExtra("postId", 0L)
        val thumbnail = intent.getByteArrayExtra("thumbnail")
        val postTitle = intent.getStringExtra("postTitle")
        val postContent = intent.getStringExtra("postContent")
        val imageByteArray: ByteArray =
            thumbnail!! //retrieve image from database in bytearray format
        //convert byteArray into bitmap
        val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)


        //assume the user ID 1 logging into the app, this must be delete later on when combined
        //user.userID instead depend on how kajie do his login
        //val userID = 1L


        postContentVM = ViewModelProvider(this).get(TkPostContentViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val userData = AppDatabase.getInstance(application).userDAO.getLoggedInUser()
                val userID = userData.id
                postContentVM.userReactionPreviously(userID, postID)
                val profilePic = ProfilePicConverter().extractImage(userData.profileImage!!)
                binding.topBarUserProfilePic.setImageBitmap(profilePic)
                lifecycleScope.launch {
                    binding.postContentCommentButton.setOnClickListener {
                        val postCommentDialog = PostComment(postID, userData)
                        postCommentDialog.show(supportFragmentManager, "post_comment_dialog")
                        //Log.d("postCommentDialog", "postContentCommentButton clicked");
                    }
                }


                binding.postContentLikeButton.setOnClickListener {
                    likeButtonReact(
                        postContentVM, postViewModel,
                        AppDatabase.getInstance(application).userReactDAO, userID, postID
                    )
                }
                binding.postContentDislikeButton.setOnClickListener {
                    dislikeButtonReact(
                        postContentVM, postViewModel,
                        AppDatabase.getInstance(application).userReactDAO, userID, postID
                    )
                }
                if(userData.is_super){
                    binding.deletePostButton?.visibility = View.VISIBLE
                    binding.editPostButton?.visibility = View.VISIBLE
                }else{
                    binding.deletePostButton?.visibility = View.GONE
                    binding.editPostButton?.visibility = View.GONE
                }
            }
        }

        binding.PostThumbnail.setImageBitmap(bitmap)
        binding.postContentTitle.text = postTitle
        binding.postContentFullContent.text = postContent

        postContentVM.setSpecificPost(postID)
        postContentVM.postUpdateLiveData.observe(this) { postUpdate ->
            binding.postContentLikeAmount.text = postUpdate.totalLikes.toString()
            binding.postContentDislikeAmount.text = postUpdate.totalDislikes.toString()
            binding.postContentCommentAmount.text = postUpdate.totalComments.toString()
        }

        postContentVM.userReactLiveData.observe(this) { userReact ->
            if (userReact?.reaction != null && userReact.reaction == 1) {
                binding.postContentLikeButton.setImageResource(R.drawable.thumb_up_green)
            } else if (userReact?.reaction != null && userReact.reaction == 0) {
                binding.postContentDislikeButton.setImageResource(R.drawable.thumb_down_red)
            } else if (userReact == null) {
                Log.d("test", "its null")
            }
        }

//        ensure that the UI interaction is performed on the correct thread
//        and that the coroutine is cancelled when the Activity or Fragment is destroyed.


        binding.toolbarBackButton?.setOnClickListener {
            onBackPressed()
        }



        binding.deletePostButton?.setOnClickListener {
            AlertDialog.Builder(this@TkPostContent)
                .setMessage(R.string.confirm_delete_post)
                .setPositiveButton(R.string.delete) { _, _ ->
                    postContentVM.deletePost(postID)
                    onBackPressed()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }

        binding.editPostButton?.setOnClickListener {
            val dialogView = LayoutInflater.from(this@TkPostContent)
                .inflate(R.layout.edit_post_dialog, null)
            val titleEditText = dialogView.findViewById<EditText>(R.id.modifyTitle)
            titleEditText.setText(postTitle)
            val contentEditText = dialogView.findViewById<EditText>(R.id.modifyContent)
            contentEditText.setText(postContent)

            AlertDialog.Builder(this@TkPostContent)
                .setView(dialogView)
                .setPositiveButton(R.string.save) { _, _ ->
                    val newPostTitle = titleEditText.text.toString()
                    val newPostContent = contentEditText.text.toString()
                    postContentVM.confirmedModifyPost(postID, newPostTitle, newPostContent)
                    binding.postContentTitle.text = newPostTitle
                    binding.postContentFullContent.text = newPostContent
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }

        binding.sharePostButton?.setOnClickListener {
            postContentVM.sharePost(this,postTitle!!,postContent!!)
        }
    }

    private fun likeButtonReact(postContentVM: TkPostContentViewModel,postViewModel: PostViewModel,
                                userReactDao: UserReactDAO,userID: Int, postID: Long){
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val currentUserReaction: UserReactData? =
                    userReactDao.getAUserReactOnAPost(userID, postID)

                if (currentUserReaction == null) {
                    withContext(Dispatchers.IO) {
                        userReactDao.insertUserReaction(UserReactData(userID, postID, 1))
                        postViewModel.incrementLikes(postID)
                        postContentVM.increaseLikes()
                    }
                    withContext(Dispatchers.Main) {
                        binding.postContentLikeButton.setImageResource(R.drawable.thumb_up_green)
                    }
                } else if (currentUserReaction.reaction == 0) {
                    withContext(Dispatchers.IO) {
                        userReactDao.updateUserReaction(UserReactData(userID, postID, 1))
                        postViewModel.incrementLikes(postID)
                        postViewModel.decrementDislikes(postID)
                        postContentVM.increaseLikes()
                        postContentVM.decreaseDislikes()
                    }
                    withContext(Dispatchers.Main) {
                        binding.postContentLikeButton.setImageResource(R.drawable.thumb_up_green)
                        binding.postContentDislikeButton.setImageResource(R.drawable.thumb_down)
                    }
                } else if (currentUserReaction.reaction == 1) {
                    withContext(Dispatchers.IO) {
                        postViewModel.decrementLikes(postID)
                        userReactDao.deleteUserReaction(userID, postID)
                        postContentVM.decreaseLikes()
                    }
                    withContext(Dispatchers.Main) {
                        binding.postContentLikeButton.setImageResource(R.drawable.thumb_up)
                    }
                }

            }
        }
    }

    private fun dislikeButtonReact(postContentVM: TkPostContentViewModel,postViewModel: PostViewModel,
                                   userReactDao: UserReactDAO,userID: Int, postID: Long){
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val currentUserReaction: UserReactData? =
                    userReactDao.getAUserReactOnAPost(userID, postID)

                if (currentUserReaction == null) {
                    withContext(Dispatchers.IO) {
                        userReactDao.insertUserReaction(UserReactData(userID, postID, 0))
                        postViewModel.incrementDislikes(postID)
                        postContentVM.increaseDislikes()
                    }
                    withContext(Dispatchers.Main) {
                        binding.postContentDislikeButton.setImageResource(R.drawable.thumb_down_red)
                    }
                } else if (currentUserReaction.reaction == 1) {
                    withContext(Dispatchers.IO) {
                        userReactDao.updateUserReaction(UserReactData(userID, postID, 0))
                        postViewModel.incrementDislikes(postID)
                        postViewModel.decrementLikes(postID)
                        postContentVM.increaseDislikes()
                        postContentVM.decreaseLikes()
                    }
                    withContext(Dispatchers.Main) {
                        binding.postContentDislikeButton.setImageResource(R.drawable.thumb_down_red)
                        binding.postContentLikeButton.setImageResource(R.drawable.thumb_up)
                    }
                } else if (currentUserReaction.reaction == 0) {
                    withContext(Dispatchers.IO) {
                        postViewModel.decrementDislikes(postID)
                        userReactDao.deleteUserReaction(userID, postID)
                        postContentVM.decreaseDislikes()
                    }
                    withContext(Dispatchers.Main) {
                        binding.postContentDislikeButton.setImageResource(R.drawable.thumb_down)
                    }
                }

            }
        }
    }
}

