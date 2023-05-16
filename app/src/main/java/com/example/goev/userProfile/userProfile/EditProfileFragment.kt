package com.example.goev.userProfile.userProfile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.goev.ProfilePicConverter
import com.example.goev.R
import com.example.goev.databinding.ActivityEditProfileBinding

class EditProfileFragment : Fragment() {

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var imageView: ImageView
    private var byteArray: ByteArray? = null
    private lateinit var imageInBitmap: Bitmap

    companion object{
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivityEditProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)

        imageView = binding.editProfilePicture

        binding.backButton.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_editProfileFragment_to_myProfileFragment)
        }

        binding.editPictureTextClickable.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/jpeg"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        binding.doneEditButton.setOnClickListener { view: View ->
            viewModel.getLoggedInUser { userData ->
                if (userData != null) {
                    // Get the updated values
                    val profileName = binding.editFullName.text.toString()
                    val phoneNumber = binding.editPhoneNumber.text.toString()

                    if(byteArray == null && userData.profileImage != null) {
                        val bitmap = ProfilePicConverter().extractImage(userData.profileImage)
                        byteArray = userData.profileImage
                        if (bitmap != null) {
                            activity?.runOnUiThread {
                                binding.editProfilePicture.setImageBitmap(bitmap)
                            }
                        }
                    }

                    // Update the user profile
                    viewModel.updateUserProfileInfo(
                        byteArray!!,
                        profileName,
                        phoneNumber,
                    )
                }
            }
            view.findNavController().navigate(R.id.action_editProfileFragment_to_myProfileFragment)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val imageUri = data?.data
            if(imageUri != null){
                imageView.setImageURI(imageUri)
                imageInBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                byteArray = ProfilePicConverter().convertImage(imageInBitmap)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLoggedInUser { userData ->
            if (userData != null) {
                // Display the current user profile values
                binding.editFullName.setText(userData.profileName)
                binding.editPhoneNumber.setText(userData.phoneNumber)

                // If the user has a profile picture, display it
                if (userData.profileImage != null && byteArray == null) {
                    activity?.runOnUiThread {
                        imageView.setImageBitmap(ProfilePicConverter().extractImage(userData.profileImage))
                    }
                }
            }
        }
    }
}
