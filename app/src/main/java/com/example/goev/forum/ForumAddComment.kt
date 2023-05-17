package com.example.goev.forum

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.goev.MainActivity
import com.example.goev.R
import com.example.goev.databinding.FragmentForumAddCommentBinding
import com.example.goev.databinding.FragmentForumAddPostBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match


class ForumAddComment : Fragment() {
    private lateinit var binding: FragmentForumAddCommentBinding
    private val shareViewModel: ForumViewModel by activityViewModels()
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentBinding = FragmentForumAddCommentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding.cancelButton?.setOnClickListener() {
            view?.findNavController()
                ?.navigate(R.id.action_forumAddComment_to_forumPostDetailsFragment)
        }
        var postId = arguments?.getInt("postId")
        var initialCommentId = arguments?.getInt("initialCommentId")
        var parentCommentId = arguments?.getInt("parentCommentId")
        var commentId = arguments?.getInt("commentId")
        var replyUserName = arguments?.getString("replyUserName")
        var userName = arguments?.getString("userName")
        var isReply = arguments?.getBoolean("isReply")
        var content = arguments?.getString("content")

        if (commentId == -1) {
            if (isReply == true) {
                binding.addCommentText.text = "Reply to " + replyUserName
                binding.commentContentEditField.hint = "Reply"
                binding.postButton.setOnClickListener() {
                    if (binding.commentContentEditField.text.isNotEmpty()) {
                        if (initialCommentId != null) {
                            if (parentCommentId != null) {
                                if (postId != null) {
                                    shareViewModel.addforumReplies(
                                        initialCommentId,
                                        parentCommentId,
                                        postId,
                                        binding.commentContentEditField.text.toString()
                                    )
                                }
                            }
                        }
                        val toast = Toast.makeText(context, "Reply Posted", Toast.LENGTH_SHORT)
                        toast.show()
                        bundle.apply { putString("postId", postId.toString()) }
                        view?.findNavController()
                            ?.navigate(
                                R.id.action_forumAddComment_to_forumPostDetailsFragment,
                                bundle
                            )

                    } else {
                        val toast = Toast.makeText(context, "Reply is Empty", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }

            } else {
                binding.addCommentText.text = "Comment as " + userName
                binding.commentContentEditField.hint = "Comment"
                binding.postButton.setOnClickListener() {
                    if (binding.commentContentEditField.text.isNotEmpty()) {
                        if (initialCommentId != null) {
                            if (parentCommentId != null) {
                                if (postId != null) {
                                    shareViewModel.addforumComment(
                                        postId,
                                        binding.commentContentEditField.text.toString()
                                    )
                                }
                            }
                        }
                        val toast = Toast.makeText(context, "Comment Posted", Toast.LENGTH_SHORT)
                        toast.show()
                        bundle.apply { putString("postId", postId.toString()) }
                        view?.findNavController()
                            ?.navigate(
                                R.id.action_forumAddComment_to_forumPostDetailsFragment,
                                bundle
                            )

                    } else {
                        val toast = Toast.makeText(context, "Comment is Empty", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
            }
        } else {
            if (isReply == true) {
                binding.addCommentText.text = "Edit Reply to " + replyUserName
                binding.commentContentEditField.hint = "Reply"
                binding.commentContentEditField.text =
                    Editable.Factory.getInstance().newEditable(content)
                binding.postButton.text = "Update"

                if (binding.commentContentEditField.text.isNotEmpty()) {
                    if (initialCommentId != null) {
                        if (parentCommentId != null) {
                            if (postId != null) {
                                shareViewModel.addforumComment(
                                    postId,
                                    binding.commentContentEditField.text.toString()
                                )
                            }
                        }
                    }
                    val toast = Toast.makeText(context, "Reply Posted", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    val toast = Toast.makeText(context, "Reply is Empty", Toast.LENGTH_SHORT)
                    toast.show()

                }

            } else {
                binding.addCommentText.text = "Edit Comment as " + userName
                binding.commentContentEditField.hint = "Comment"
                binding.commentContentEditField.text =
                    Editable.Factory.getInstance().newEditable(content)
                binding.postButton.text = "Update"

                if (binding.commentContentEditField.text.isNotEmpty()) {
                    if (initialCommentId != null) {
                        if (parentCommentId != null) {
                            if (postId != null) {
                                shareViewModel.addforumComment(
                                    postId,
                                    binding.commentContentEditField.text.toString()
                                )
                            }
                        }
                    }
                    val toast = Toast.makeText(context, "Reply Posted", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    val toast = Toast.makeText(context, "Reply is Empty", Toast.LENGTH_SHORT)
                    toast.show()

                }
            }
        }


        return binding.root
    }

    override fun onResume() {
        // Hides bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        (requireActivity() as MainActivity).hideTopAppBar()
        super.onResume()
    }

    override fun onPause() {
        // Unhidden bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        (requireActivity() as MainActivity).showTopAppBar()
        super.onPause()
    }
}