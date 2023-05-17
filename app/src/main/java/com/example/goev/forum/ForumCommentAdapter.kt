package com.example.goev.forum

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.R
import com.example.goev.database.forum.ForumPostComment
import com.example.goev.databinding.CommentViewBinding
import com.github.marlonlom.utilities.timeago.TimeAgo


class ForumCommentAdapter() :
    RecyclerView.Adapter<ForumCommentAdapter.PostCommentViewHolder>() {
    private var forumCommentList: List<ForumPostComment>? = emptyList()
    private var isButtonClicked = false
    private var forumCommentAdapterButtonClickListener: OnForumCommentAdapterButtonClickListener? =
        null

    fun setForumCommentAdapterButtonClickListener(listener: OnForumCommentAdapterButtonClickListener) {
        this.forumCommentAdapterButtonClickListener = listener
    }

    fun updateData(newData: List<ForumPostComment>?) {
        forumCommentList = newData
        notifyDataSetChanged()
    }


    /**
     * Provides a reference for the views needed to display items in your list.
     */
    inner class PostCommentViewHolder(private val binding: CommentViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(forumComment: ForumPostComment) {
            binding.username.text = forumComment.userData.userName
            binding.commentContent.text = forumComment.forumCommentData.content
            binding.noOfLike.text = forumComment.noOfLike.toString()
            binding.noOfDislike.text = forumComment.noOfDislike.toString()
            binding.timestamp.text = TimeAgo.using(forumComment.forumCommentData.updatedAt)
            var status = forumComment.status
            var showMoreisClicked: Boolean = false
            binding.noOfReplies.text = forumComment.noOfReplies.toString() + " Replies"


            binding.executePendingBindings()

            fun dpToPx(dp: Float): Int {
                return TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp,
                    Resources.getSystem().displayMetrics
                ).toInt()
            }

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

                when (status) {
                    "like" -> {
                        forumCommentAdapterButtonClickListener?.onDeleteLikeClicked(
                            adapterPosition,
                            forumComment.forumCommentData.commentId,
                            forumComment.forumCommentData.postId
                        )
                    }
                    "none" -> {
                        forumCommentAdapterButtonClickListener?.onAddLikeClicked(
                            adapterPosition,
                            forumComment.forumCommentData.commentId,
                            forumComment.forumCommentData.postId
                        )
                    }
                    "dislike" -> {
                        forumCommentAdapterButtonClickListener?.onDisliketoLikeClicked(
                            adapterPosition,
                            forumComment.forumCommentData.commentId,
                            forumComment.forumCommentData.postId
                        )

                    }
                    else -> {
                        Log.d("Error Tag", "Error")
                    }
                }
            }
            binding.dislikeButton.setOnClickListener() {

                when (status) {
                    "dislike" -> {
                        forumCommentAdapterButtonClickListener?.onDeleteDislikeClicked(
                            adapterPosition,
                            forumComment.forumCommentData.commentId,
                            forumComment.forumCommentData.postId
                        )
                    }
                    "none" -> {
                        forumCommentAdapterButtonClickListener?.onAddDislikeClicked(
                            adapterPosition,
                            forumComment.forumCommentData.commentId,
                            forumComment.forumCommentData.postId
                        )
                    }
                    "like" -> {
                        forumCommentAdapterButtonClickListener?.onLiketoDislikeClicked(
                            adapterPosition,
                            forumComment.forumCommentData.commentId,
                            forumComment.forumCommentData.postId
                        )
                    }
                    else -> {
                        Log.d("Error Tag", "Error")
                    }
                }
            }

            if (forumComment.isReply == true) {
                val layoutParams =
                    binding.commentLayout.layoutParams as ViewGroup.MarginLayoutParams
                val rightMarginDp = 50f // Desired right margin in dp
                val rightMarginPx = dpToPx(rightMarginDp)

                layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin,
                    rightMarginPx,
                    layoutParams.bottomMargin
                )
                binding.commentLayout.layoutParams = layoutParams
                binding.tag.text = "@" + forumComment.respondentUserData.userName
                binding.tag.visibility = View.VISIBLE
            } else {
                binding.tag.visibility = View.GONE
            }


            if (forumComment.noOfReplies > 0) {
                binding.noOfReplies.visibility = View.VISIBLE
                binding.showMore.visibility = View.GONE
            } else {
                binding.noOfReplies.visibility = View.GONE
                binding.showMore.visibility = View.GONE
            }


            binding.reply.setOnClickListener {
                forumCommentAdapterButtonClickListener?.onAddCommentClicked(
                    adapterPosition,
                    if(forumComment.parentCommentId == -1){forumComment.forumCommentData.commentId}else{forumComment.parentCommentId},
                    forumComment.forumCommentData.commentId,
                    -1,
                    forumComment.forumCommentData.postId,
                    true,
                    forumComment.userData.userName,
                    forumComment.userName,
                    ""
                )

            }
            binding.more.visibility = View.INVISIBLE


        }
    }

    override fun getItemCount(): Int {
        return forumCommentList?.size ?: 0
    }


    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ForumCommentAdapter.PostCommentViewHolder {
        val binding = CommentViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostCommentViewHolder(binding)
    }

    /**
     * Replaces the content of an existing view with new data
     */

    override fun onBindViewHolder(
        holder: ForumCommentAdapter.PostCommentViewHolder,
        position: Int
    ) {
        val commentOnView = forumCommentList!![position]
        holder.bind(commentOnView)

    }

    interface OnForumCommentAdapterButtonClickListener {
        fun onAddLikeClicked(position: Int, commentId: Int, postId: Int)
        fun onDeleteLikeClicked(position: Int, commentId: Int, postId: Int)
        fun onAddDislikeClicked(position: Int, commentId: Int, postId: Int)
        fun onDeleteDislikeClicked(position: Int, commentId: Int, postId: Int)
        fun onLiketoDislikeClicked(position: Int, commentId: Int, postId: Int)
        fun onDisliketoLikeClicked(position: Int, commentId: Int, postId: Int)
        fun onAddCommentClicked(
            position: Int, parentCommentId: Int,
            initialCommentId: Int,
            commentId: Int,
            postId: Int,
            isReply: Boolean,
            replyUserName: String,
            userName: String,
            content: String
        )

    }


}
