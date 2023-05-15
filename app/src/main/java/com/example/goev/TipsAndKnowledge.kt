package com.example.goev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserDatabase
import com.example.goev.databases.post.PostViewModel
import com.example.goev.databinding.FragmentTipsAndKnowledgeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TipsAndKnowledge : Fragment() {
    private lateinit var mPostViewModel: PostViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTipsAndKnowledgeBinding>(inflater,
            R.layout.fragment_tips_and_knowledge,container,false)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val loggedInUser =
                    UserDatabase.getInstance(requireContext()).userDao().getLoggedInUser()
                val profilePic = ProfilePicConverter().extractImage(loggedInUser.profileImage!!)
                //top right corner profile pic
                binding.topBarUserProfilePic.setImageBitmap(profilePic)


                //add new post (by admin only)
                // maybe add check function to see if a user login via admin acc / normal acc
                if(loggedInUser!!.is_super){
                    binding.addPostButton?.visibility = View.VISIBLE
                }
                else{
                    binding.addPostButton?.visibility = View.GONE
                }
            }
        }


        binding.addPostButton?.setOnClickListener{
                view : View ->
            view.findNavController().navigate(R.id.action_tipsAndKnowledge_to_addPostAdmin)
        }

        //RecyclerView content                        requireContext()
        val adapter = ListAdapter(requireContext()) //for fragment to pass data to next acitivty (list adapter pass to post content)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultBackButton?.visibility = View.GONE


        mPostViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        mPostViewModel.readAllData.observe(viewLifecycleOwner, Observer { post->
            adapter.setData(post)
        })

        binding.searchButton?.setOnClickListener {
            val query = binding.seachQueryInput?.text.toString()
            if (!query.isNullOrEmpty()) {
                mPostViewModel.searchPost(query)
                adapter.setSearchData(mPostViewModel.searchedData)
            }
        }
        mPostViewModel.searchedData.observe(viewLifecycleOwner, Observer { post ->
            adapter.setData(post)
            binding.searchResultBackButton?.visibility = View.VISIBLE
        })

        binding.searchResultBackButton?.setOnClickListener {
            binding.searchResultBackButton?.visibility = View.GONE
            mPostViewModel.readAllData.observe(viewLifecycleOwner, Observer { post->
                adapter.setData(post)
            })
        }

        return binding.root
    }

}
