package com.example.goev.chargingstationtracker.editchargingstation


import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.goev.R
import com.example.goev.databaseChargingStation.ChargingStation
import com.example.goev.databaseChargingStation.ChargingStationViewModel
import com.example.goev.databinding.FragmentEditStationBinding
import com.example.goev.editstation.EditStationFragmentArgs
import com.example.goev.editstation.EditStationFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class EditStationFragment : Fragment() {

    private var _binding: FragmentEditStationBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<EditStationFragmentArgs>()
    private lateinit var mChargingStationViewModel: ChargingStationViewModel
    private lateinit var editStationViewModel: EditStationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_station, container, false)

        // Initialize viewModel to retrieve chargingStation data from database
        mChargingStationViewModel =
            ViewModelProvider(this)[ChargingStationViewModel::class.java]

        // Initialize edit station view model to maintain edit text data on configuration changes
        editStationViewModel = ViewModelProvider(this)[EditStationViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve current charging station from database
        mChargingStationViewModel.getChargingStationById(args.currentChargingStationId) { chargingStation ->
            if (chargingStation != null) {
                activity?.runOnUiThread {
                    //Update edit station with the item passed from view station
                    if (editStationViewModel.stationAddress == null && editStationViewModel.stationName == null) {
                        binding.editChargingStationNameEditText.setText(chargingStation.name)
                        binding.editChargingStationAddressEditText.setText(chargingStation.address)
                    }

                    //edit button
                    binding.saveBtn.setOnClickListener {
                        updateDatabase(chargingStation)
                    }

                    binding.cancelBtn.setOnClickListener {
                        navigateToViewStationFragment()
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
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.id_validation_msg),
                    Toast.LENGTH_SHORT
                )
                    .show()
                navigateToViewStationFragment()
            }
        }
    }

    private fun updateDatabase(currentChargingStation: ChargingStation) {
        val chargingStationName = binding.editChargingStationNameEditText.text.toString()
        val chargingStationAddress = binding.editChargingStationAddressEditText.text.toString()
        val chargingStationImage = currentChargingStation.image

        if (inputCheck(chargingStationName, chargingStationAddress)) {
            // Create chargingStation Object
            val chargingStation = ChargingStation(
                args.currentChargingStationId,
                chargingStationName,
                chargingStationAddress,
                chargingStationImage
            )
            // Update current chargingStation
            mChargingStationViewModel.updateChargingStation(chargingStation)
            showEditSuccessMessage()

            // Introduce a short delay before navigating
            lifecycleScope.launch {
                delay(500)
                // navigate back
                navigateToViewStationFragment()
            }
        } else {
            activity?.runOnUiThread {
                binding.editChargingStationNameEditText.clearFocus()
                binding.editChargingStationAddressEditText.clearFocus()
                if (TextUtils.isEmpty(chargingStationName))
                    binding.editChargingStationNameTextfield.error =
                        getString(R.string.empty_field_helper_msg)
                if (TextUtils.isEmpty(chargingStationAddress))
                    binding.editChargingStationAddressTextfield.error =
                        getString(R.string.empty_field_helper_msg)
                showEmptyInputMessage()
            }
        }
    }

    private fun showEditSuccessMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.edit_success_msg),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showEmptyInputMessage() {
        val contextView = binding.saveBtn
        Snackbar.make(contextView, R.string.error_add_message, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.divider)
            .show()
    }

    override fun onResume() {
        // Hides bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
        super.onResume()
    }

    override fun onPause() {
        // Unhidden bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Save current edit text info into EditChargingStationViewModel
        editStationViewModel.stationName = binding.editChargingStationNameEditText.text.toString()
        editStationViewModel.stationAddress =
            binding.editChargingStationAddressEditText.text.toString()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.empty_action_bar_menu, menu)
    }

    private fun navigateToViewStationFragment() {
        val action =
            EditStationFragmentDirections.actionEditStationFragmentToViewStationFragment(args.currentChargingStationId)
        findNavController().navigate(action)
    }

    // Check if user inputted all fields
    private fun inputCheck(chargingStationName: String, chargingStationAddress: String): Boolean {
        return !(TextUtils.isEmpty(chargingStationName) || TextUtils.isEmpty(chargingStationAddress))
    }
}