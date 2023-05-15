package com.example.goev.chargingstationlist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goev.R
import com.example.goev.database.ChargingStationViewModel
import com.example.goev.databinding.FragmentTrackerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class TrackerFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!

    private lateinit var mChargingStationViewModel: ChargingStationViewModel
    private val adapter = TrackerListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracker, container, false)

        // RecyclerView

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // charging station view model
        mChargingStationViewModel = ViewModelProvider(this).get(ChargingStationViewModel::class.java)
        mChargingStationViewModel.readAllData.observe(viewLifecycleOwner, Observer { chargingStation ->
            adapter.setData(chargingStation)
        })

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.tracker_action_bar_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        // make sure the searchView takes up the whole app bar
        searchItem.setOnActionExpandListener(onActionExpandListener(menu))

        // Configure the search info and add any event listeners.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Navigate to settings screen.
                Log.i("ChargingLocatorList", "search action triggered")
                Toast.makeText(requireContext(), "Search selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_view_user_info -> {
                // Save profile changes.
                Log.i("ChargingLocatorList", "userInfo in fragment called")
                Toast.makeText(requireContext(), "User profile selected", Toast.LENGTH_SHORT).show()
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
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_all_dialog_title))
            .setMessage(resources.getString(R.string.delete_all_supporting_text))
            .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                mChargingStationViewModel.deleteAllChargingStations()
                Toast.makeText(requireContext(),
                    getString(R.string.delete_all_success_msg),
                    Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun onActionExpandListener(menu: Menu) =
        object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                // Hide the other menu items when the search view is expanded
                menu.findItem(R.id.action_view_user_info).isVisible = false
//                menu.findItem(R.id.action_delete).isVisible = false
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                // Show the other menu items when the search view is collapsed
                menu.findItem(R.id.action_view_user_info).isVisible = true
//                menu.findItem(R.id.action_delete).isVisible = true
                return true
            }
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