package com.example.goev

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.goev.database.ChargingStationViewModel
import com.example.goev.databinding.FragmentViewStationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ViewStationFragment : Fragment() {

    private var _binding: FragmentViewStationBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ViewStationFragmentArgs>()

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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_station, container, false)

        // Initialize viewModel
        mChargingStationViewModel = ViewModelProvider(this).get(ChargingStationViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Update view station with the item passed from recyclerView
        binding.viewEvStationName.text = args.currentChargingStation.name
        binding.viewEvStationAddress.text = args.currentChargingStation.address

        binding.editButton.setOnClickListener {
            navigateToEditStationFragment()
        }

        binding.navigateFab.setOnClickListener {
            googleMapsIntent()
        }
    }

    private fun googleMapsIntent() {
        val encodedAddress = Uri.encode(args.currentChargingStation.address)
        val googleMapsUri = Uri.parse("geo:0,0?q=$encodedAddress")
        val webMapsUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$encodedAddress")
        val intent = Intent(Intent.ACTION_VIEW, googleMapsUri)
        val webIntent = Intent(Intent.ACTION_VIEW, webMapsUri)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Google Maps is not installed, fallback to opening Google Maps in a web browser
            try {
                startActivity(webIntent)
            } catch (e: ActivityNotFoundException) {
                // Handle the case where neither Google Maps nor a web browser is available
                val contextView = binding.navigateFab
                Snackbar.make(contextView, R.string.no_google_maps_message, Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.navigateFab)
                    .show()
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
        inflater.inflate(R.menu.view_tracker_action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                deleteChargingStation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteChargingStation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_dialog_title))
            .setMessage(resources.getString(R.string.delete_supporting_text))
            .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                mChargingStationViewModel.deleteChargingStation(args.currentChargingStation)
                Toast.makeText(requireContext(),
                    "Successfully deleted: ${args.currentChargingStation.name}",
                    Toast.LENGTH_SHORT).show()
                navigateToTrackerFragment()
            }
            .show()
    }

    private fun navigateToTrackerFragment() {
        val action = ViewStationFragmentDirections.actionViewStationFragmentToTrackerFragment()
        findNavController().navigate(action)
    }

    private fun navigateToEditStationFragment() {
        val action = ViewStationFragmentDirections.actionViewStationFragmentToEditStationFragment(args.currentChargingStation)
        findNavController().navigate(action)
    }
}