package com.example.goev

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.goev.database.ChargingStation
import com.example.goev.database.ChargingStationViewModel
import com.example.goev.databinding.FragmentAddStationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar


class AddStationFragment : Fragment() {
    private var _binding: FragmentAddStationBinding? = null
    private val binding get() = _binding!!

    
    private lateinit var mChargingStationViewModel: ChargingStationViewModel


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

        mChargingStationViewModel = ViewModelProvider(this).get(ChargingStationViewModel::class.java)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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


        if(inputCheck(chargingStationName, chargingStationAddress)) {
            // Create chargingStation Object
            val chargingStation = ChargingStation(0, chargingStationName, chargingStationAddress)
            // Add data to database
            mChargingStationViewModel.addChargingStation(chargingStation)
            Toast.makeText(requireContext(), "Successfully added charging station.", Toast.LENGTH_SHORT).show()
            // navigate back
            navigateToTrackerFragment()
        } else {
            binding.chargingStationNameEditText.clearFocus()
            binding.chargingStationAddressEditText.clearFocus()
            // Check for empty fields, give error message to those that are empty
            if (TextUtils.isEmpty(chargingStationName))
                binding.chargingStationNameTextfield.error = "Empty Field"
            if (TextUtils.isEmpty(chargingStationAddress))
                binding.chargingStationAddressTextfield.error = "Empty Field"

            val contextView = binding.addBtn
            Snackbar.make(contextView, R.string.error_add_message, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.divider)
                .show()
        }
    }
}