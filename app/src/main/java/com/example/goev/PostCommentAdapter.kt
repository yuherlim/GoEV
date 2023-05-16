package com.example.goev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.database.user.UserDao
import com.example.goev.database.user.UserData
import com.example.goev.databases.TipsAndKnowledgeDatabase
import com.example.goev.databases.postcomment.TkPostCommentData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostCommentAdapter(val userDAO: UserDao,
                         val user: UserData,
    private val onDeleteComment: (comment: TkPostCommentData) -> Unit,
    private val onEditComment: (comment: TkPostCommentData) -> Unit):
    RecyclerView.Adapter<PostCommentAdapter.ViewHolder>() {
    var comments = mutableListOf<TkPostCommentData>()
    fun settingComments(comments: List<TkPostCommentData>) {
        this.comments.clear()
        this.comments.addAll(comments)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tk_comment_cardview, parent, false)
        return ViewHolder(itemView, comments, onDeleteComment, onEditComment)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        holder.userName.text = comment.userName
        holder.comment.text = comment.comment
        holder.commentTime.text = comment.commentTime.toString()
        //profile pic has to modified based on kajie database. most likely will need
        // to retrieve and convert into bitmap
        GlobalScope.launch(Dispatchers.IO) {
            //profile pic has to modified based on kajie database. most likely will need
            // to retrieve and convert into bitmap
            val userData = userDAO.getUserByID(comment.userId)
            val profilePicInBitmap = ProfilePicConverter().extractImage(userData!!.profileImage)
            withContext(Dispatchers.Main) {
                holder.itemView.findViewById<ImageView>(R.id.userProfilePic).setImageBitmap(profilePicInBitmap)
            }
        }
        // if it is admin user, everyone's edit comment and delete comment button is visible
        //&& user.isSuper
        if (comment.userId == user.id || user.is_super) {
            holder.itemView.findViewById<ImageView>(R.id.deleteCommentButton).visibility = View.VISIBLE
            holder.itemView.findViewById<ImageView>(R.id.editCommentButton).visibility = View.VISIBLE
        } else {
            holder.itemView.findViewById<ImageView>(R.id.deleteCommentButton).visibility = View.GONE
            holder.itemView.findViewById<ImageView>(R.id.editCommentButton).visibility = View.GONE
        }
        //postContentVM = ViewModelProvider(this).get(TkPostContentViewModel::class.java)

        holder.itemView.findViewById<ImageView>(R.id.deleteCommentButton).setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setMessage(R.string.confirm_delete_comment)
                .setPositiveButton(R.string.delete) { _, _ ->
                    onDeleteComment(comment)

                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }

        holder.itemView.findViewById<ImageView>(R.id.editCommentButton).setOnClickListener {
            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.edit_comment_dialog, null)
            val editText = dialogView.findViewById<EditText>(R.id.editCommentText)
            editText.setText(comment.comment)

            AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .setPositiveButton(R.string.save) { _, _ ->
                    val newComment = comment.copy(comment = editText.text.toString())
                    editComment(comment,newComment)
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    private fun editComment(comment: TkPostCommentData, newComment: TkPostCommentData) {
        val commentIndex = comments.indexOf(comment)
        if (commentIndex != -1) {
            comments[commentIndex] = newComment
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View, private val comments: List<TkPostCommentData>,
                     private val onDeleteComment: (comment: TkPostCommentData) -> Unit,
                     private val onEditComment: (comment: TkPostCommentData) -> Unit
                     )
        : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userName)
        val comment: TextView = itemView.findViewById(R.id.comment)
        val commentTime: TextView = itemView.findViewById(R.id.commentDate)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteCommentButton)
        val editButton: ImageView = itemView.findViewById(R.id.editCommentButton)

        init {
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val comment = comments[position]
                    onDeleteComment(comment)
                }
            }

            editButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val comment = comments[position]
                    onEditComment(comment)
                }
            }

        }
    }
}

