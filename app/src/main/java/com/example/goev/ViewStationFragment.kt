package com.example.goev

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.goev.database.ChargingStation
import com.example.goev.database.ChargingStationViewModel
import com.example.goev.databinding.FragmentViewStationBinding
import com.example.goev.utils.ChargingStationImageConverter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewStationFragment : Fragment() {

    private var _binding: FragmentViewStationBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ViewStationFragmentArgs>()

    private lateinit var mChargingStationViewModel: ChargingStationViewModel



    private var byteArray: ByteArray? = null
    private lateinit var imageInBitmap: Bitmap

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

        // Retrieve current charging station from database
        mChargingStationViewModel.getChargingStationById(args.currentChargingStationId) { chargingStation ->
            if (chargingStation != null) {
                // Ensure view updates are done on ui thread
                activity?.runOnUiThread {
                    //Update view station with the item passed from recyclerView
                    binding.viewEvStationName.text = chargingStation.name
                    binding.viewEvStationAddress.text = chargingStation.address
                    setViewImage(chargingStation)

                    binding.navigateFab.setOnClickListener {
                        googleMapsIntent(chargingStation)
                    }

                    binding.editImageButton.setOnClickListener {
                        intentToRetrieveImage()
                    }

                    binding.editButton.setOnClickListener {
                        navigateToEditStationFragment()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Valid id is not passed in.", Toast.LENGTH_SHORT).show()
                navigateToTrackerFragment()
            }
        }
    }

    private fun setViewImage(chargingStation: ChargingStation) {
        if (chargingStation.image != null) {
            val bitmap = ChargingStationImageConverter().extractImage(chargingStation.image)
            if (bitmap != null) {
                activity?.runOnUiThread {
                    binding.viewEvStationImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun updateDatabaseWithImage() {
        if (byteArray != null) {
            // Update current chargingStation
            mChargingStationViewModel.updateChargingStationImage(byteArray!!, args.currentChargingStationId)
            imageEditSuccessMsg()
        }
    }

    private fun imageEditSuccessMsg() {
        val contextView = binding.navigateFab
        Snackbar.make(contextView, R.string.image_edit_success_msg, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.navigateFab)
            .show()
    }

    private fun googleMapsIntent(chargingStation: ChargingStation) {
        val encodedAddress = Uri.encode(chargingStation.address)
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
                activity?.runOnUiThread {
                    // Handle the case where neither Google Maps nor a web browser is available
                    val contextView = binding.navigateFab
                    Snackbar.make(contextView, R.string.no_google_maps_message, Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.navigateFab)
                        .show()
                }
            }
        }
    }

    override fun onResume() {
        // Hides bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE

        // Update image with new edited image
        updateViewImage()
        super.onResume()
    }

    override fun onPause() {
        // Unhides bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        super.onPause()
    }

    private fun updateViewImage() {
        if (byteArray != null) {
            val bitmap = ChargingStationImageConverter().extractImage(byteArray)
            if (bitmap != null) {
                activity?.runOnUiThread {
                    binding.viewEvStationImage.setImageBitmap(bitmap)
                }
            }
        }
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
        mChargingStationViewModel.getChargingStationById(args.currentChargingStationId) { chargingStation ->
            if (chargingStation != null) {
                // run dialog on main thread
                activity?.runOnUiThread {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.delete_dialog_title))
                        .setMessage(resources.getString(R.string.delete_supporting_text))
                        .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                            // Respond to negative button press
                        }
                        .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                            mChargingStationViewModel.deleteChargingStation(chargingStation)
                            Toast.makeText(requireContext(),
                                "Successfully deleted: ${chargingStation.name}",
                                Toast.LENGTH_SHORT).show()
                            // Introduce a short delay before navigating
                            lifecycleScope.launch {
                                delay(500)
                                navigateToTrackerFragment()
                            }
                        }
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "Valid id is not passed in.", Toast.LENGTH_SHORT).show()
                navigateToTrackerFragment()
            }
        }
    }

    private fun navigateToTrackerFragment() {
        val action = ViewStationFragmentDirections.actionViewStationFragmentToTrackerFragment()
        findNavController().navigate(action)
    }

    private fun navigateToEditStationFragment() {
        val action = ViewStationFragmentDirections.actionViewStationFragmentToEditStationFragment(args.currentChargingStationId)
        findNavController().navigate(action)
    }

    private fun intentToRetrieveImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg")
        startActivityForResult(intent, AddStationFragment.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == AddStationFragment.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val imageUri = data?.data
            if(imageUri != null){
                imageInBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                byteArray = ChargingStationImageConverter().convertImage(imageInBitmap)
                updateDatabaseWithImage()
            }
        }
    }
}