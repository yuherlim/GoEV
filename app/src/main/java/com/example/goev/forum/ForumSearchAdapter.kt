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
import kotlinx.coroutines.NonDisposableHandle.parent


class ForumSearchAdapter() :
    RecyclerView.Adapter<ForumSearchAdapter.PostSearchViewHolder>() {
    private var forumPostList: List<ForumPost>? = emptyList()
    private var isButtonClicked = false
    private var forumSearchAdapterButtonClickListener: OnForumSearchAdapterButtonClickListener? = null

    fun setOnForumSearchAdapterButtonClickListener(listener: OnForumSearchAdapterButtonClickListener) {
        this.forumSearchAdapterButtonClickListener = listener
    }

    fun updateData(newData: List<ForumPost>?) {
        forumPostList = newData
        notifyDataSetChanged()
    }


    /**
     * Provides a reference for the views needed to display items in your list.
     */
    inner class PostSearchViewHolder(private val binding: PostViewBinding) :
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
            binding.timestamp.text = TimeAgo.using(forumPost.forumPostData.updatedAt)
            binding.noOfLike.visibility = View.GONE
            binding.noOfDislike.visibility = View.GONE
            binding.noOfComment.visibility = View.GONE
            binding.likeButton.visibility = View.GONE
            binding.commentButton.visibility = View.GONE
            binding.dislikeButton.visibility = View.GONE

            binding.executePendingBindings()


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
                forumSearchAdapterButtonClickListener?.onPostDetailsClicked(forumPost.forumPostData!!.postId)

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
    ): PostSearchViewHolder {
        val binding = PostViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostSearchViewHolder(binding)
    }

    /**
     * Replaces the content of an existing view with new data
     */

    override fun onBindViewHolder(holder:PostSearchViewHolder, position: Int) {
        val postOnView = forumPostList!![position]
        holder.bind(postOnView)

    }

    interface OnForumSearchAdapterButtonClickListener {
        fun onPostDetailsClicked(postId: Int)


    }


}
