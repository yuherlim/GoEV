package com.example.goev.forum

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.MainActivity
import com.example.goev.R
import com.example.goev.databinding.FragmentForumFilterPostBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ForumMyLikePost : Fragment(), ForumPostAdapter.OnForumPostAdapterButtonClickListener {
    private var binding : FragmentForumFilterPostBinding? = null
    private val shareViewModel : ForumViewModel by activityViewModels()
    private var recyclerView : RecyclerView? = null
    private lateinit var forumPostAdapter: ForumPostAdapter
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareViewModel.getCurrentLoginUser()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentBinding = FragmentForumFilterPostBinding.inflate(inflater,container,false)
        binding = fragmentBinding
        binding?.backButton?.setOnClickListener(){
            view?.findNavController()?.navigate(R.id.action_forumMyLikePost_to_myProfileFragment)
        }

        recyclerView = binding?.forumCommentRecycleView
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        forumPostAdapter = ForumPostAdapter()
        forumPostAdapter.setOnInteractionButtonClickListener(this)
        recyclerView?.adapter = forumPostAdapter
        shareViewModel.loadMyLikePostList()
        shareViewModel.forumDataList.observe(viewLifecycleOwner, Observer { forumData ->
            forumPostAdapter.updateData(
                forumData
            ) })
        return binding?.root
    }

    override fun onAddLikeClicked(position: Int, postId: Int) {
        shareViewModel.addMyLikePostListLike(postId)
    }

    override fun onAddDislikeClicked(position: Int, postId: Int) {
        shareViewModel.addMyLikePostListDislike(postId)
    }

    override fun onDeleteLikeClicked(position: Int, postId: Int) {
        shareViewModel.deleteMyLikePostListLike(postId)

    }

    override fun onDeleteDislikeClicked(position: Int, postId: Int) {
        shareViewModel.deleteMyLikePostListDislike(postId)
    }

    override fun onLiketoDislikeClicked(position: Int, postId: Int) {
        shareViewModel.myLikePostListLiketoDislike(postId)

    }

    override fun onDisliketoLikeClicked(position: Int, postId: Int) {
        shareViewModel.myLikePostListDisiketoLike(postId)
    }

    override fun onPostDetailsClicked(postId: Int) {
        shareViewModel.loadPost(postId)
        bundle.apply {putString("postId", postId.toString())
                putString("fromWhere","MyLikePost")}
        view?.findNavController()?.navigate(R.id.action_forumMyLikePost_to_forumPostDetailsFragment,bundle)
    }

    override fun onDialogClicked(postId: Int,createdTime : Long,content:String,title:String) {}

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