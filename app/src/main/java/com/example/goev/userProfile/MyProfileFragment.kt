package com.example.goev.userProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.goev.databinding.ActivityMyProfileBinding

class MyProfileFragment : Fragment() {

    private lateinit var binding: ActivityMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMyProfileBinding.inflate(inflater, container, false)



        return binding.root
    }

}