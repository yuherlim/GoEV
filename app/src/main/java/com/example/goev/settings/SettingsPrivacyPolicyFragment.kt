package com.example.goev.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.goev.MainActivity
import com.example.goev.R
import com.example.goev.databinding.ActivitySettingsPrivacyPolicyBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class SettingsPrivacyPolicyFragment : Fragment() {

    private lateinit var binding: ActivitySettingsPrivacyPolicyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivitySettingsPrivacyPolicyBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_settingsPrivacyPolicyFragment_to_settingsPrivacyFragment)
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