package com.example.goev.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.goev.R
import com.example.goev.databinding.ActivitySettingsPrivacyAccountBinding

class SettingsPrivacyAccountFragment : Fragment() {

    private lateinit var binding: ActivitySettingsPrivacyAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivitySettingsPrivacyAccountBinding.inflate(inflater, container, false)

        binding.changeFullNameButton.setOnClickListener { view : View ->

        }

        binding.changeEmailButton.setOnClickListener { view: View ->

        }

        binding.changePasswordButton.setOnClickListener { view: View ->

        }


        return binding.root
    }
}