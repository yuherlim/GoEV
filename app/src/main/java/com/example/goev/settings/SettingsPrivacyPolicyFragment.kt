package com.example.goev.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.databinding.ActivitySettingsPrivacyPolicyBinding


class SettingsPrivacyPolicyFragment : Fragment() {

    private lateinit var binding: ActivitySettingsPrivacyPolicyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivitySettingsPrivacyPolicyBinding.inflate(inflater, container, false)


        return binding.root
    }

}