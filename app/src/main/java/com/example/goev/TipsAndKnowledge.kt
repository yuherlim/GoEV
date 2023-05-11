package com.example.goev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goev.databases.post.PostViewModel
import com.example.goev.databases.tempUser.UserData
import com.example.goev.databases.tempUser.UserViewModel
import com.example.goev.databinding.FragmentTipsAndKnowledgeBinding

class TipsAndKnowledge : Fragment() {
    private lateinit var mPostViewModel: PostViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTipsAndKnowledgeBinding>(inflater,
            R.layout.fragment_tips_and_knowledge,container,false)

        //add new post (by admin only)
        // maybe add check function to see if a user login via admin acc / normal acc
        binding.floatingActionButton.setOnClickListener{
                view : View ->
            view.findNavController().navigate(R.id.action_tipsAndKnowledge_to_addPostAdmin)
        }

        //RecyclerView content                        requireContext()
        val adapter = ListAdapter(requireContext()) //for fragment to pass data to next acitivty (list adapter pass to post content)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        mPostViewModel.readAllData.observe(viewLifecycleOwner, Observer { post->
            adapter.setData(post)
        })

        return binding.root
    }
}
