package com.example.goev

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.goev.databases.PostViewModel
import com.example.goev.databases.TkPostData
import com.example.goev.databinding.FragmentAddPostAdminBinding

class AddPostAdmin : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddPostAdminBinding>(inflater,
            R.layout.fragment_add_post_admin,container,false)
        val postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        binding.postButton.setOnClickListener{
            val title: String = binding.postTitle.text.toString()
            val content: String = binding.postContent.text.toString()
            if(inputCheck(title,content)){
                //Create post object
                val post = TkPostData(title,content)
                // Add data to database
                postViewModel.addPost(post)
                //display succeed message
                Toast.makeText(requireContext(),"Post uploaded",Toast.LENGTH_LONG).show()
                //Navigate back to previous page
                findNavController().navigate(R.id.action_addPostAdmin_to_tipsAndKnowledge)
            }
            else{
                Toast.makeText(requireContext(),"Please fill in all necessary fields",Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun inputCheck(title: String, content: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }

}