package com.example.goev.forum

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.R
import com.example.goev.database.forum.ForumPost
import com.example.goev.databinding.PostViewBinding
import com.github.marlonlom.utilities.timeago.TimeAgo


class ForumPostAdapter() :
    RecyclerView.Adapter<ForumPostAdapter.PostViewHolder>() {
    private var forumPostList: List<ForumPost>? = emptyList()
    private var isButtonClicked = false
    private var forumPostAdapterButtonClickListener: OnForumPostAdapterButtonClickListener? = null

    fun setOnInteractionButtonClickListener(listener: OnForumPostAdapterButtonClickListener) {
        this.forumPostAdapterButtonClickListener = listener
    }

    fun updateData(newData: List<ForumPost>?) {
        forumPostList = newData
        notifyDataSetChanged()
    }


    /**
     * Provides a reference for the views needed to display items in your list.
     */
    inner class PostViewHolder(private val binding: PostViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(forumPost: ForumPost) {
            val maxLength = 100
            val ellipsis = "..."
            val suffix = "Read More"
            var postContentExpandable: Boolean
            var postContentExpandState = false
            var status = forumPost.status
            binding.username.text = forumPost.userData.userName
            binding.postContent.text = forumPost.forumPostData.content
            binding.postTitle.text = forumPost.forumPostData.title
            binding.noOfLike.text = forumPost.noOfLike.toString()
            binding.noOfDislike.text = forumPost.noOfDislike.toString()
            binding.noOfComment.text = forumPost.noOfComment.toString()
            binding.timestamp.text = TimeAgo.using(forumPost.forumPostData.updatedAt)
            binding.executePendingBindings()
            fun setIcon(status: String) {
                Log.d("setIcon", "set icon called")
                when (status) {
                    "like" -> {
                        binding.likeButton.setImageResource(R.drawable.baseline_thumb_up_alt_24)
                        binding.dislikeButton.setImageResource(R.drawable.baseline_thumb_down_off_alt_24)
                    }
                    "dislike" -> {
                        binding.likeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                        binding.dislikeButton.setImageResource(R.drawable.baseline_thumb_down_alt_24)
                    }
                    "none" -> {
                        binding.likeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                        binding.dislikeButton.setImageResource(R.drawable.baseline_thumb_down_off_alt_24)
                    }
                }
            }
            setIcon(status)
            binding.likeButton.setOnClickListener() {
                Log.d(forumPost.forumPostData.postId.toString()+"idLike", forumPost.status)
                Log.d(forumPost.forumPostData.postId.toString()+"idLikeLike", forumPost.noOfLike.toString())
                Log.d(forumPost.forumPostData.postId.toString()+"idDislikeLike", forumPost.noOfDislike.toString())
                Log.d(forumPost.forumPostData.postId.toString()+"idcommentLike", forumPost.noOfComment.toString())

                    when (status) {
                        "like" -> {
                            forumPostAdapterButtonClickListener?.onDeleteLikeClicked(adapterPosition,forumPost.forumPostData.postId)
                        }
                        "none" -> {
                            forumPostAdapterButtonClickListener?.onAddLikeClicked(adapterPosition,forumPost.forumPostData.postId)
                        }
                        "dislike" -> {
                            forumPostAdapterButtonClickListener?.onDisliketoLikeClicked(adapterPosition,forumPost.forumPostData.postId)

                        }
                        else -> {
                            Log.d("Error Tag", "Error")  }
                    }
            }
            binding.dislikeButton.setOnClickListener() {
            Log.d(forumPost.forumPostData.postId.toString()+"idDislike", forumPost.status)
            Log.d(forumPost.forumPostData.postId.toString()+"idLikeDislike", forumPost.noOfLike.toString())
            Log.d(forumPost.forumPostData.postId.toString()+"idDislikeDislike", forumPost.noOfDislike.toString())
            Log.d(forumPost.forumPostData.postId.toString()+"idcommentDislike", forumPost.noOfComment.toString())

            when (status) {
                        "dislike" -> {
                            forumPostAdapterButtonClickListener?.onDeleteDislikeClicked(adapterPosition,forumPost.forumPostData.postId)
                        }
                        "none" -> {
                            forumPostAdapterButtonClickListener?.onAddDislikeClicked(adapterPosition,forumPost.forumPostData.postId)
                        }
                        "like" -> {
                            forumPostAdapterButtonClickListener?.onLiketoDislikeClicked(adapterPosition,forumPost.forumPostData.postId)
                        }
                        else -> {
                            Log.d("Error Tag", "Error")  }
                    }}

            fun truncateText(postContentExpandState: Boolean) {
                if (postContentExpandState == false) {
                    val truncatedText =
                        binding.postContent.text.substring(0, maxLength - ellipsis.length)

                    // Add the ellipsis and suffix to the truncated text
                    val text = "$truncatedText$ellipsis $suffix"

                    // Set the text to your TextView
                    binding.postContent.text = text
                } else {
                    binding.postContent.text = forumPost.forumPostData!!.content
                }
            }

            if (binding.postContent.text.length > maxLength) {
                postContentExpandable = true
                postContentExpandState = false
                truncateText(postContentExpandState)
                // Truncate the text

            } else {
                postContentExpandable = false
                // If the text is shorter than the maximum length, display it as is
                binding.postContent.text = binding.postContent.text
            }

            binding.postContent.setOnClickListener() {
                if (postContentExpandable == true) {
                    if (postContentExpandState == true) {
                        postContentExpandState = false
                        truncateText(postContentExpandState)
                    } else {
                        postContentExpandState = true
                        truncateText(postContentExpandState)
                    }
                } else {
                }
            }

            binding.postCardView.setOnClickListener() {
                forumPostAdapterButtonClickListener?.onPostDetailsClicked(forumPost.forumPostData!!.postId)

            }

            binding.commentButton.setOnClickListener(){
                forumPostAdapterButtonClickListener?.onPostDetailsClicked(forumPost.forumPostData!!.postId)
            }

            if(forumPost.forumPostData.userId  == forumPost.userId) {
                binding.more.visibility = View.VISIBLE
                binding.more.setOnClickListener(){
                    forumPostAdapterButtonClickListener?.onDialogClicked(forumPost.forumPostData!!.postId,forumPost.forumPostData!!.createdAt,forumPost.forumPostData!!.content,forumPost.forumPostData!!.title)
                    Log.d("2","2")
                }
            }else{
                binding.more.visibility = View.INVISIBLE
            }


        }
    }

    override fun getItemCount(): Int {
        return forumPostList?.size ?: 0
    }


    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PostViewHolder {
        val binding = PostViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    /**
     * Replaces the content of an existing view with new data
     */

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postOnView = forumPostList!![position]
        holder.bind(postOnView)

    }
    interface OnForumPostAdapterButtonClickListener {
        fun onAddLikeClicked(position: Int, postId: Int)
        fun onDeleteLikeClicked(position: Int, postId: Int)
        fun onAddDislikeClicked(position: Int, postId: Int)
        fun onDeleteDislikeClicked(position: Int, postId: Int)
        fun onLiketoDislikeClicked(position: Int, postId: Int)
        fun onDisliketoLikeClicked(position: Int, postId: Int)
        fun onPostDetailsClicked(postId: Int)
        fun onDialogClicked(postId: Int,createdTime : Long,content:String,title:String)


    }


}
