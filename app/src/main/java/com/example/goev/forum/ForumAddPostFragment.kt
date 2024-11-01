package com.example.goev.forum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.MainActivity
import com.example.goev.R
import com.example.goev.databinding.FragmentForumAddPostBinding
import com.example.goev.databinding.FragmentForumMainPageBinding
import com.example.goev.forum.ForumViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForumAddPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForumAddPostFragment : Fragment() {

    private var binding: FragmentForumAddPostBinding? = null
    private val shareViewModel: ForumViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var fragmentBinding = FragmentForumAddPostBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding?.cancelButton?.setOnClickListener() {
            view?.findNavController()
                ?.navigate(R.id.action_forumAddPostFragment_to_forumMainPageFragment)
    }

        var postId = arguments?.getString("postId")?.toInt()
        var content = arguments?.getString("content")
        var time = arguments?.getLong("time")
        var title = arguments?.getString("title")
        var mode = arguments?.getString("postMode")


        if (mode == "EditPost"){
            binding?.fragmentName?.text = "Edit Post"
            binding?.postButton?.text = "Post"
            binding?.postTitleEditField?.setText(title)
            binding?.postContentEditField?.setText(content)
        }


        binding?.postButton?.setOnClickListener{
            if (mode == "EditPost") {
                if (binding?.postTitleEditField?.text?.isNotEmpty() == true) {
                    if (binding?.postContentEditField?.text?.isNotEmpty() == true) {
                        if (postId != null) {
                            if (time != null) {
                                shareViewModel.editPost(
                                    postId,
                                    binding?.postContentEditField?.text.toString() ,
                                    binding?.postTitleEditField?.text.toString(),
                                    time
                                )
                            }
                        }
                        val toast = Toast.makeText(context, "Post Updated", Toast.LENGTH_LONG)
                        toast.show()
                        view?.findNavController()
                            ?.navigate(R.id.action_forumAddPostFragment_to_forumMainPageFragment)
                    } else {
                        val toast = Toast.makeText(context, "Content is Empty", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                } else {
                    val toast = Toast.makeText(context, "Title is Empty", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }else{
                if (binding?.postTitleEditField?.text?.isNotEmpty() == true) {
                    if (binding?.postContentEditField?.text?.isNotEmpty() == true) {
                        shareViewModel.addPost(
                            binding?.postTitleEditField?.text.toString(),
                            binding?.postContentEditField?.text.toString()
                        )
                        val toast = Toast.makeText(context, "Post Created", Toast.LENGTH_SHORT)
                        toast.show()
                        view?.findNavController()
                            ?.navigate(R.id.action_forumAddPostFragment_to_forumMainPageFragment)
                    } else {
                        val toast = Toast.makeText(context, "Content is Empty", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                } else {
                    val toast = Toast.makeText(context, "Title is Empty", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }



        return binding?.root
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