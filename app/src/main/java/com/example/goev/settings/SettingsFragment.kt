package com.example.goev.settings

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.goev.MainActivity
import com.example.goev.ProfilePicConverter
import com.example.goev.R
import com.example.goev.databinding.ActivitySettingsBinding
import com.example.goev.userProfile.userProfile.EditProfileViewModel
import com.google.android.material.appbar.MaterialToolbar

@Suppress("DEPRECATION")
class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: ActivitySettingsBinding

    private lateinit var editProfileViewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = ActivitySettingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        //Initialize editProfileViewModel
        editProfileViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)

        binding.privacyButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_settingsPrivacyFragment)
        }

        binding.helpButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_settingsHelpFragment)
        }

        binding.feedbackButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_settingsFeedbackFragment)
        }

        binding.aboutButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_settingsAboutFragment)
        }

        editProfileViewModel.getLoggedInUser { userData ->
            if (userData != null) {
                activity?.runOnUiThread {
                    if (userData.profileImage != null) {
                        val bitmap = ProfilePicConverter().extractImage(userData.profileImage)
                        if (bitmap != null) {
                            activity?.runOnUiThread {
                                val menu: Menu = (requireActivity() as MainActivity).findViewById<MaterialToolbar>(R.id.topAppBar).menu
                                val profileMenuItem: MenuItem? = menu.findItem(R.id.action_view_user_info)
                                profileMenuItem?.icon = BitmapDrawable(resources, bitmap)
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.settings_action_bar_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_view_user_info -> {
                findNavController().navigate(R.id.myProfileFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}