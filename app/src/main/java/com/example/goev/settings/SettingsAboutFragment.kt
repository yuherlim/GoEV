package com.example.goev.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.goev.databinding.ActivitySettingsAboutBinding

class SettingsAboutFragment : Fragment() {

    private lateinit var binding: ActivitySettingsAboutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivitySettingsAboutBinding.inflate(inflater, container, false)


        return binding.root
    }


}