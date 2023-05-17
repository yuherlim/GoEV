package com.example.goev.forum

import android.app.Dialog
import android.app.ProgressDialog.show
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.goev.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.MainActivity
import com.example.goev.databinding.FragmentForumMainPageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ForumMainPageFragment : Fragment(), ForumPostAdapter.OnForumPostAdapterButtonClickListener {
    private var binding : FragmentForumMainPageBinding? = null
   private val shareViewModel : ForumViewModel by activityViewModels()
    private var recyclerView : RecyclerView? = null
    private lateinit var forumPostAdapter: ForumPostAdapter
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareViewModel.getCurrentLoginUser()
        shareViewModel.insertSampleData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentBinding = FragmentForumMainPageBinding.inflate(inflater,container,false)
        binding = fragmentBinding
        binding?.addIcon?.setOnClickListener(){
            view?.findNavController()?.navigate(R.id.action_forumMainPageFragment_to_forumAddPostFragment)

        }
        binding?.searchIcon?.setOnClickListener(){
            view?.findNavController()?.navigate(R.id.action_forumMainPageFragment_to_forumSearchFragment)
        }
        binding?.profilePicture?.setOnClickListener {
            findNavController().navigate(R.id.myProfileFragment)
        }
        recyclerView = binding?.forumPostRecycleView
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        forumPostAdapter = ForumPostAdapter()
        forumPostAdapter.setOnInteractionButtonClickListener(this)
        recyclerView?.adapter = forumPostAdapter
        shareViewModel.loadPostList()
       shareViewModel.forumDataList.observe(viewLifecycleOwner, Observer {   forumData ->
           forumPostAdapter.updateData(
               forumData
           ) })
        return binding?.root
    }

    override fun onAddLikeClicked(position: Int, postId: Int) {
        shareViewModel.addPostListLike(postId)

    }

    override fun onAddDislikeClicked(position: Int, postId: Int) {
        shareViewModel.addPostListDislike(postId)
    }

    override fun onDeleteLikeClicked(position: Int, postId: Int) {
        shareViewModel.deletePostListLike(postId)

    }

    override fun onDeleteDislikeClicked(position: Int, postId: Int) {
        shareViewModel.deletePostListDislike(postId)
    }

    override fun onLiketoDislikeClicked(position: Int, postId: Int) {
        shareViewModel.postListLiketoDislike(postId)

    }

    override fun onDisliketoLikeClicked(position: Int, postId: Int) {
        shareViewModel.postListDisiketoLike(postId)
    }


    override fun onPostDetailsClicked(postId: Int) {
        shareViewModel.loadPost(postId)
        bundle.apply {putString("postId", postId.toString())
                putString("fromWhere","MainPage")}
        view?.findNavController()?.navigate(R.id.action_forumMainPageFragment_to_forumPostDetailsFragment,bundle)
    }

    override fun onDialogClicked(postId: Int) {
    }



    override fun onResume() {
        (requireActivity() as MainActivity).hideTopAppBar()
        super.onResume()
    }

    override fun onPause() {
        (requireActivity() as MainActivity).showTopAppBar()
        super.onPause()
    }



}


