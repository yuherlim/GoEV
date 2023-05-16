package com.example.goev.forum

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.goev.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.databinding.FragmentForumMainPageBinding
import com.example.goev.databinding.FragmentForumSearchBinding


class ForumSearchFragment : Fragment(), ForumSearchAdapter.OnForumSearchAdapterButtonClickListener {
    private var binding : FragmentForumSearchBinding? = null
   private val shareViewModel : ForumViewModel by activityViewModels()
    private var recyclerView : RecyclerView? = null
    private lateinit var forumSearchAdapter: ForumSearchAdapter
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentBinding = FragmentForumSearchBinding.inflate(inflater,container,false)
        binding = fragmentBinding

        binding?.backButton?.setOnClickListener(){
            view?.findNavController()?.navigate(R.id.action_forumSearchFragment_to_forumMainPageFragment)
        }
        binding?.searchButton?.setOnClickListener(){
            if (binding?.search?.text.toString().isNotEmpty()){
            shareViewModel.loadSearchList(binding?.search?.text.toString())
        }else{val toast = Toast.makeText(context,"search field is empty",Toast.LENGTH_SHORT)
        toast.show()}
        }


        recyclerView = binding?.forumPostRecycleView
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        forumSearchAdapter = ForumSearchAdapter()
        forumSearchAdapter.setOnForumSearchAdapterButtonClickListener(this)
        recyclerView?.adapter = forumSearchAdapter
        shareViewModel.loadPostList()
       shareViewModel.searchDataList.observe(viewLifecycleOwner, Observer {   searchData ->
           forumSearchAdapter.updateData(
               searchData
           ) })
        return binding?.root
    }


    override fun onPostDetailsClicked(postId: Int) {
        shareViewModel.loadPost(postId)
        bundle.apply {putString("postId", postId.toString())}
        view?.findNavController()?.navigate(R.id.action_forumSearchFragment_to_forumPostDetailsFragment,bundle)
    }




}


