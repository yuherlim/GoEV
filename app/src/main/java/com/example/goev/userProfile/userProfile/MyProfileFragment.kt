package com.example.goev.userProfile.userProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.goev.MainActivity
import com.example.goev.ProfilePicConverter
import com.example.goev.R
import com.example.goev.databinding.ActivityMyProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyProfileFragment : Fragment() {

    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivityMyProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MyProfileViewModel::class.java)

        binding.backButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_myProfileFragment_to_settingsFragment)
        }

        viewModel.getLoggedInUser { userData ->
            if(userData != null){
                activity?.runOnUiThread {
                    binding.profileName.text = userData.profileName
                    binding.userEmail.text = userData.email
                    if(userData.profileImage != null) {
                        val bitmap = ProfilePicConverter().extractImage(userData.profileImage)
                        if (bitmap != null) {
                            activity?.runOnUiThread {
                                binding.userProfileImage.setImageBitmap(bitmap)
                            }
                        }
                    }
                }
            }
        }

        binding.editProfileButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_myProfileFragment_to_editProfileFragment)
        }

        binding.postHistoryButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_myProfileFragment_to_forumMyLikePost)
        }

        binding.myPostButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_myProfileFragment_to_forumMyPostFragment)
        }


        binding.logOutButton.setOnClickListener { view : View ->
            viewModel.getLoggedInUser { userData ->
                if(userData != null) {
                    viewModel.updateUserLoggedIn(userData.id, false)
                }
            }
            view.findNavController().navigate(R.id.action_myProfileFragment_to_loginFragment)
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
