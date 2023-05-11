package com.example.goev.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.databinding.ActivitySettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = ActivitySettingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        binding.notificationButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_settingsNotificationsFragment)
        }

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



        return binding.root
    }

}