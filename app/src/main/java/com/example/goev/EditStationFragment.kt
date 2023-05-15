package com.example.goev

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.goev.database.ChargingStation
import com.example.goev.database.ChargingStationViewModel
import com.example.goev.databinding.FragmentEditStationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class EditStationFragment : Fragment() {

    private var _binding: FragmentEditStationBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<EditStationFragmentArgs>()

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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_station, container, false)

        // Initialize viewModel
        mChargingStationViewModel = ViewModelProvider(this).get(ChargingStationViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Update edit station with the item passed from view station
        binding.editChargingStationNameEditText.setText(args.currentChargingStation.name)
        binding.editChargingStationAddressEditText.setText(args.currentChargingStation.address)

        //edit button
        binding.saveBtn.setOnClickListener {
            updateDatabase()
        }

        binding.cancelBtn.setOnClickListener {
            navigateToViewStationFragment(args.currentChargingStation)
        }

        binding.editChargingStationAddressEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.editChargingStationAddressTextfield.error = null
            }
        }


        binding.editChargingStationNameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.editChargingStationNameTextfield.error = null
            }

        }
    }

    private fun updateDatabase() {
        val chargingStationName = binding.editChargingStationNameEditText.text.toString()
        val chargingStationAddress = binding.editChargingStationAddressEditText.text.toString()


        if(inputCheck(chargingStationName, chargingStationAddress)) {
            // Create chargingStation Object
            val chargingStation = ChargingStation(args.currentChargingStation.id, chargingStationName, chargingStationAddress)
            // Update current chargingStation
            mChargingStationViewModel.updateChargingStation(chargingStation)
            Toast.makeText(requireContext(), "Successfully edited charging station.", Toast.LENGTH_SHORT).show()
            // navigate back
            navigateToViewStationFragment(chargingStation)
        } else {
//            mChargingStationViewModel.setIsError(true)
            binding.editChargingStationNameEditText.clearFocus()
            binding.editChargingStationAddressEditText.clearFocus()
            if (TextUtils.isEmpty(chargingStationName))
                binding.editChargingStationNameTextfield.error = "Empty Field"
            if (TextUtils.isEmpty(chargingStationAddress))
                binding.editChargingStationAddressTextfield.error = "Empty Field"
            val contextView = binding.saveBtn
            Snackbar.make(contextView, R.string.error_add_message, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.divider)
                .show()
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

    private fun navigateToViewStationFragment(chargingStation: ChargingStation) {
        val action = EditStationFragmentDirections.actionEditStationFragmentToViewStationFragment(chargingStation)
        findNavController().navigate(action)
    }

    // Check if user inputted all fields
    private fun inputCheck(chargingStationName: String, chargingStationAddress: String): Boolean {
        return !(TextUtils.isEmpty(chargingStationName) || TextUtils.isEmpty(chargingStationAddress))
    }
}