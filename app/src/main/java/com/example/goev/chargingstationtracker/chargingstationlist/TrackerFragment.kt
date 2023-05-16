package com.example.goev.chargingstationtracker.chargingstationlist

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goev.MainActivity
import com.example.goev.ProfilePicConverter
import com.example.goev.R
import com.example.goev.databaseChargingStation.ChargingStationViewModel
import com.example.goev.databinding.FragmentTrackerBinding
import com.example.goev.userProfile.userProfile.EditProfileViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class TrackerFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!

    private lateinit var editProfileViewModel: EditProfileViewModel
    private lateinit var mChargingStationViewModel: ChargingStationViewModel

    private val adapter = TrackerListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracker, container, false)

        // RecyclerView
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // charging station view model
        mChargingStationViewModel =
            ViewModelProvider(this)[ChargingStationViewModel::class.java]

        // Initialize editProfileViewModel
        editProfileViewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]

        mChargingStationViewModel.readAllData.observe(
            viewLifecycleOwner
        ) { chargingStation ->
            adapter.setData(chargingStation)
        }

        updateProfilePic()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            navigateToAddStationFragment()
        }
    }

    private fun navigateToAddStationFragment() {
        val action = TrackerFragmentDirections.actionTrackerFragmentToAddStationFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.tracker_action_bar_menu, menu)

        //Reapply profile pic
        updateProfilePic()

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        // Configure the search info and add any event listeners.
        searchView.setOnQueryTextListener(this)
    }

    private fun updateProfilePic() {
        editProfileViewModel.getLoggedInUser { userData ->
            if (userData != null) {
                lifecycleScope.launch(Dispatchers.Main) {
                    if (userData.profileImage != null) {
                        val bitmap = ProfilePicConverter().extractImage(userData.profileImage)
                        if (bitmap != null) {
                            activity?.runOnUiThread {
                                val menu: Menu =
                                    (requireActivity() as MainActivity).findViewById<MaterialToolbar>(
                                        R.id.topAppBar
                                    ).menu
                                val profileMenuItem: MenuItem? =
                                    menu.findItem(R.id.action_view_user_info)
                                profileMenuItem?.icon = BitmapDrawable(resources, bitmap)
                            }
                        }
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_view_user_info -> {
                findNavController().navigate(R.id.myProfileFragment)
                true
            }
            R.id.action_delete_all -> {
                deleteAllChargingStations()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllChargingStations() {
        MaterialAlertDialogBuilder(requireContext()).setTitle(resources.getString(R.string.delete_all_dialog_title))
            .setMessage(resources.getString(R.string.delete_all_supporting_text))
            .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                // Respond to negative button press
            }.setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                mChargingStationViewModel.deleteAllChargingStations()
                Toast.makeText(
                    requireContext(), getString(R.string.delete_all_success_msg), Toast.LENGTH_SHORT
                ).show()
            }.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        mChargingStationViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.setData(it)
            }
        }
    }
}