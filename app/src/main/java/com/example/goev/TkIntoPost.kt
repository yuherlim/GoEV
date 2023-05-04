package com.example.goev

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.goev.databinding.FragmentTipsAndKnowledgeBinding
import com.example.goev.databinding.FragmentTkIntoPostBinding
import java.util.*

class TkIntoPost : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTkIntoPostBinding>(
            inflater,
            R.layout.fragment_tk_into_post, container, false
        )

        //receive data from previous fragment
        val postID = arguments?.getLong("postId")
        val postTitle = arguments?.getString("postTitle")
        val postContent = arguments?.getString("postContent")
        val postTotallikes = arguments?.getInt("postTotalLikes")
        val postTotalDislikes = arguments?.getInt("postTotalDislikes")
        val postTotalComments = arguments?.getInt("postTotalComments")
        val postDate = Date(arguments?.getLong("postDate") ?: 0)

        binding.intoPostTitle.text = postTitle
        binding.intoPostContent.text = postContent
        binding.intoPostTotalLikes.text = postTotallikes.toString()
        binding.intoPostTotalDislikes.text = postTotalDislikes.toString()
        binding.intoPostTotalComments.text = postTotalComments.toString()
        binding.intoPostDate.text = postDate.toString()

        binding.backFloatingbutton.setOnClickListener {view ->
            view.findNavController().navigate(R.id.action_tkIntoPost_to_tipsAndKnowledge)
        }

        return binding.root
    }

}