package com.example.goev.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.databinding.ActivityRegisterBinding
import com.example.goev.databinding.ActivitySettingsNotificationsBinding


class SettingsNotificationsFragment : Fragment() {

    private lateinit var binding: ActivitySettingsNotificationsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivitySettingsNotificationsBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_settingsNotificationsFragment_to_settingsFragment)
        }

        return binding.root
    }

}