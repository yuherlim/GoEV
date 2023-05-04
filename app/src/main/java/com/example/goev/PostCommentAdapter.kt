package com.example.goev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.databases.postcomment.TkPostCommentData

class PostCommentAdapter: RecyclerView.Adapter<PostCommentAdapter.ViewHolder>() {
    private val comments = mutableListOf<TkPostCommentData>()

    fun setComments(comments: List<TkPostCommentData>) {
        this.comments.clear()
        this.comments.addAll(comments)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tk_comment_cardview, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        holder.userName.text = comment.userName
        holder.comment.text = comment.comment
        holder.commentTime.text = comment.commentTime.toString()
        holder.itemView.findViewById<ImageView>(R.id.userProfilePic).setImageResource(R.drawable.ellipse_71)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userName)
        val comment: TextView = itemView.findViewById(R.id.comment)
        val commentTime: TextView = itemView.findViewById(R.id.commentDate)
    }
}