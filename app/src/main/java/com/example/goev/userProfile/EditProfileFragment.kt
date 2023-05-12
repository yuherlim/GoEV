package com.example.goev.userProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.goev.R
import com.example.goev.databinding.ActivityEditProfileBinding

class EditProfileFragment : Fragment() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityEditProfileBinding.inflate(inflater, container, false)

        // Set up the Spinner
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderSpinner.adapter = adapter

        return binding.root
    }
}