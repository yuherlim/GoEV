package com.example.goev

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goev.databases.TipsAndKnowledgeDatabase
import com.example.goev.databases.postcomment.TkPostCommentData
import com.example.goev.databases.tempUser.UserData
import com.example.goev.databases.tempUser.UserViewModel
import com.example.goev.databinding.FragmentPostCommentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostComment(private val postID: Long, private val user: UserData) : BottomSheetDialogFragment() {
    private lateinit var commentAdapter: PostCommentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentPostCommentBinding>(
            inflater,
            R.layout.fragment_post_comment, container, false
        )

        commentAdapter = PostCommentAdapter(user, ::deleteComment, ::updateEditedComment)
        val recyclerView = binding.recyclerView2
        recyclerView.adapter = commentAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadComments(postID)

        binding.postComment.setOnClickListener {
            val comment = binding.writeComment.text.toString()
            if (comment.isNotEmpty()) {
                val newComment = TkPostCommentData(user.userID, user.userName, comment, postID)
                insertComment(newComment)
                binding.writeComment.text.clear()
                loadComments(postID)
            }
        }
        binding.toolbarBackButton.setOnClickListener {
            dismiss()
        }

        var isAscending = false
        binding.filterCommentButton.setOnClickListener {
            val popup = PopupMenu(context, binding.filterCommentButton)
            popup.menuInflater.inflate(R.menu.filter_comment_popup, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.sort_ascending -> {
                        isAscending = true
                        loadComments(postID, isAscending)
                        true
                    }
                    R.id.sort_descending -> {
                        isAscending = false
                        loadComments(postID, isAscending)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        return binding.root
    }

    private fun loadComments(postID: Long, isAscending: Boolean = false) {
        val commentDao = TipsAndKnowledgeDatabase.getInstance(requireContext()).postCommentDAO
        commentDao.getCommentsForPost(postID).observe(viewLifecycleOwner, Observer { comments ->
            val sortedComments = if (isAscending) {
                comments.sortedBy { it.commentTime }
            } else {
                comments.sortedByDescending { it.commentTime }
            }
            commentAdapter.settingComments(sortedComments)
        })
    }

    private fun insertComment(comment: TkPostCommentData) {
        val commentDao = TipsAndKnowledgeDatabase.getInstance(requireContext()).postCommentDAO
        val postDao = TipsAndKnowledgeDatabase.getInstance(requireContext()).postDAO

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                commentDao.addComment(comment)
                postDao.commentCountIncrement(postID)
            }
        }
    }

    private fun deleteComment(comment: TkPostCommentData) {
        val commentDao = TipsAndKnowledgeDatabase.getInstance(requireContext()).postCommentDAO
        val postDao = TipsAndKnowledgeDatabase.getInstance(requireContext()).postDAO
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                commentDao.deleteComment(comment)
                postDao.commentCountDecrement(postID)
            }
        }
    }

    private fun updateEditedComment(comment: TkPostCommentData){
        val commentDao = TipsAndKnowledgeDatabase.getInstance(requireContext()).postCommentDAO
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                commentDao.editComment(comment)
            }
        }
    }

}