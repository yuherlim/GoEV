package com.example.goev.userProfile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.goev.R
import com.example.goev.database.user.UserData
import com.example.goev.databinding.ActivityEditProfileBinding
import com.example.goev.register.RegisterViewModel


class EditProfileFragment : Fragment() {

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivityEditProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)

        // Set up the Spinner
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderSpinner.adapter = adapter

        binding.editPictureTextClickable.setOnClickListener {
            //change profile picture

        }


        val gender = binding.genderSpinner.selectedItem.toString()
        val profileName = binding.editFullName.text.toString()
        val phoneNumber = binding.editPhoneNumber.text.toString()
        val email = binding.editEmail.text.toString()


//            viewModel.updateUserProfileInfo(
//                id,
//                profileName,
//                email,
//                phoneNumber,
//                gender
//            )


        return binding.root
    }


}