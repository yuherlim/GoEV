package com.example.goev.forum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.MainActivity
import com.example.goev.R
import com.example.goev.database.forum.ForumPost
import com.example.goev.databinding.FragmentForumPostDetailsBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForumPostDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForumPostDetailsFragment : Fragment(),ForumCommentAdapter.OnForumCommentAdapterButtonClickListener {
    private var binding: FragmentForumPostDetailsBinding? = null
    private val shareViewModel: ForumViewModel by activityViewModels()
    private var recyclerView: RecyclerView? = null
    lateinit var forum: ForumPost
    private var status = ""
    val bundle = Bundle()


    private lateinit var forumCommentAdapter: ForumCommentAdapter

    fun setIcon(status: String) {
        when (status) {
            "like" -> {
                binding!!.likeButton.setImageResource(R.drawable.baseline_thumb_up_alt_24)
                binding!!.dislikeButton.setImageResource(R.drawable.baseline_thumb_down_off_alt_24)
            }
            "dislike" -> {
                binding!!.likeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                binding!!.dislikeButton.setImageResource(R.drawable.baseline_thumb_down_alt_24)
            }
            "none" -> {
                binding!!.likeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                binding!!.dislikeButton.setImageResource(R.drawable.baseline_thumb_down_off_alt_24)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentBinding = FragmentForumPostDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        var postId = arguments?.getString("postId")?.toInt()


        shareViewModel.forumData.observe(viewLifecycleOwner) { forumData ->
            binding!!.username.text = forumData.forumPostData.userId
            binding!!.timestamp.text = TimeAgo.using(forumData.forumPostData.updatedAt)
            binding!!.postContent.text = forumData.forumPostData.content
            binding!!.postTitle.text = forumData.forumPostData.title
            binding!!.noOfLike.text = forumData.noOfLike.toString()
            binding!!.noOfDislike.text = forumData.noOfDislike.toString()
            binding!!.noOfComment.text = forumData.noOfComment.toString()
            status = forumData.status
            forum = forumData
            setIcon(status)
            postId = forumData.forumPostData.postId
        }

        recyclerView = binding?.forumCommentRecycleView
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        forumCommentAdapter = ForumCommentAdapter()
        forumCommentAdapter.setForumCommentAdapterButtonClickListener(this)
        recyclerView?.adapter = forumCommentAdapter
        postId?.let { shareViewModel.loadCommentList(it) }
        shareViewModel.forumCommentDataList.observe(viewLifecycleOwner, Observer{ forumCommentData ->
            forumCommentAdapter.updateData(
                forumCommentData
            )
        })

        binding?.backButton?.setOnClickListener() {
            view?.findNavController()
                ?.navigate(R.id.action_forumPostDetailsFragment_to_forumMainPageFragment)
        }

        binding?.likeButton?.setOnClickListener() {
            when (status) {
                "like" -> {
                    postId?.let { it1 -> shareViewModel.deletePostLike(it1) }
                }
                "none" -> {
                    postId?.let { it1 -> shareViewModel.addPostLike(it1) }
                }
                "dislike" -> {
                    postId?.let { it1 -> shareViewModel.postDisiketoLike(it1) }
                }
                else -> {
                    Log.d("XPost", "Cant Clicked")
                }
            }
        }

        binding?.dislikeButton?.setOnClickListener() {
            when (status) {
                "dislike" -> {
                    postId?.let { it1 -> shareViewModel.deletePostDislike(it1) }
                }
                "none" -> {
                    postId?.let { it1 -> shareViewModel.addPostDislike(it1) }

                }
                "like" -> {
                    postId?.let { it1 -> shareViewModel.postLiketoDislike(it1) }

                }
                else -> {
                    Log.d("XPost", "Cant Clicked")
                }
            }
        }

        binding?.commentButton?.setOnClickListener(){
            postId?.let { it1 -> onAddCommentClicked(-1,-1,-1,-1, it1,false,"", shareViewModel.userid,"") }

        }
        binding?.more?.visibility = View.INVISIBLE

        return binding?.root
    }

    override fun onAddLikeClicked(position: Int, commentId: Int, postId : Int) {
        shareViewModel.addCommentLike(commentId,postId)
    }

    override fun onAddDislikeClicked(position: Int, commentId: Int, postId : Int) {
        shareViewModel.addCommentDislike(commentId,postId)
    }

    override fun onDeleteLikeClicked(position: Int, commentId: Int, postId : Int) {
        shareViewModel.deleteCommentLike(commentId,postId)
    }

    override fun onDeleteDislikeClicked(position: Int, commentId: Int, postId : Int) {
        shareViewModel.deleteCommentDislike(commentId,postId)
    }

    override fun onLiketoDislikeClicked(position: Int, commentId:Int , postId : Int) {
        shareViewModel.commentLiketoDislike(commentId,postId)
    }

    override fun onDisliketoLikeClicked(position: Int, commentId: Int, postId : Int) {
        shareViewModel.commentDisiketoLike(commentId,postId)
    }

    override fun onAddCommentClicked(
        position: Int,parentCommentId :Int,
        initialCommentId: Int,
        commentId: Int,
        postId:Int,
        isReply:Boolean,
        replyUserId: String,
        userId:String,
    content: String){
        bundle.apply {
            putInt("postId", postId)
            putInt("initialCommentId",initialCommentId)
            putInt("parentCommentId",parentCommentId)
            putInt("commentId",commentId)
            putString("replyUserId",replyUserId)
            putString("userId",userId)
            putBoolean("isReply",isReply)
            putString("content",content)
        }
        view?.findNavController()?.navigate(R.id.action_forumPostDetailsFragment_to_forumAddComment,bundle)

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


