package com.example.goev

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.goev.database.ChargingStation
import com.example.goev.database.ChargingStationViewModel
import com.example.goev.databinding.FragmentAddStationBinding
import com.example.goev.utils.ChargingStationImageConverter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AddStationFragment : Fragment() {
    private var _binding: FragmentAddStationBinding? = null
    private val binding get() = _binding!!

    private lateinit var mChargingStationViewModel: ChargingStationViewModel
    private var byteArray: ByteArray? = null
    private lateinit var imageInBitmap: Bitmap

    companion object{
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_station, container, false)

        // Initialize charging station view model
        mChargingStationViewModel = ViewModelProvider(this).get(ChargingStationViewModel::class.java)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addImgButton.setOnClickListener {
            intentToRetrieveImage()
        }

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }

        binding.cancelBtn.setOnClickListener {
            navigateToTrackerFragment()
        }

        binding.chargingStationAddressEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.chargingStationAddressTextfield.error = null
            }
        }


        binding.chargingStationNameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.chargingStationNameTextfield.error = null
            }

        }
    }

    private fun intentToRetrieveImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg")
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val imageUri = data?.data
            if(imageUri != null){
                imageInBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                byteArray = ChargingStationImageConverter().convertImage(imageInBitmap)
                imageUploadedSuccessMessage()
            }
        }
    }

    private fun imageUploadedSuccessMessage() {
        val contextView = binding.addImgButton
        Snackbar.make(contextView, R.string.img_add_success_msg, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.divider)
            .show()
    }

    override fun onResume() {
        // Hides bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        super.onResume()
    }

    override fun onPause() {
        // Unhides bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.empty_action_bar_menu, menu)
    }

    private fun navigateToTrackerFragment() {
        val action = AddStationFragmentDirections.actionAddStationFragmentToTrackerFragment()
        findNavController().navigate(action)
    }

    // Check if user inputted all fields
    private fun inputCheck(chargingStationName: String, chargingStationAddress: String): Boolean {
        return !(TextUtils.isEmpty(chargingStationName) || TextUtils.isEmpty(chargingStationAddress))
    }


    private fun insertDataToDatabase() {
        val chargingStationName = binding.chargingStationNameEditText.text.toString()
        val chargingStationAddress = binding.chargingStationAddressEditText.text.toString()
        val chargingStationImage = byteArray
        
        if(inputCheck(chargingStationName, chargingStationAddress)) {
            // Create chargingStation Object
            val chargingStation = ChargingStation(0, chargingStationName, chargingStationAddress, chargingStationImage)
            // Add data to database
            mChargingStationViewModel.addChargingStation(chargingStation)
            Toast.makeText(requireContext(), "Successfully added charging station.", Toast.LENGTH_SHORT).show()

            // Introduce a short delay before navigating
            lifecycleScope.launch {
                delay(500)
                // navigate back
                navigateToTrackerFragment()
            }
        } else {
            binding.chargingStationNameEditText.clearFocus()
            binding.chargingStationAddressEditText.clearFocus()

            // Check for empty fields, give error message to those that are empty
            if (TextUtils.isEmpty(chargingStationName))
                binding.chargingStationNameTextfield.error = "Empty Field"
            if (TextUtils.isEmpty(chargingStationAddress))
                binding.chargingStationAddressTextfield.error = "Empty Field"

            emptyFieldsErrorMessage()
        }
    }

    private fun emptyFieldsErrorMessage() {
        val contextView = binding.addBtn
        Snackbar.make(contextView, R.string.error_add_message, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.divider)
            .show()
    }

}