package com.example.goev.chargingstationlist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.goev.R
import com.example.goev.database.ChargingStationViewModel
import com.example.goev.databinding.FragmentTrackerBinding
import com.google.android.material.snackbar.Snackbar

class TrackerFragment : Fragment() {
    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!

//    private lateinit var mChargingStationViewModel: ChargingStationViewModel

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

//        mChargingStationViewModel = ViewModelProvider(this).get(ChargingStationViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            navigateToAddStationFragment()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        Log.i("TrackerFragment", "onResume triggered.")
//        if (mChargingStationViewModel.onAdd.value == true) {
//            Log.i("TrackerFragment", "onAdd is true")
//            //snackbar message: record added
//            val contextView = binding.constraintLayout
//            Snackbar.make(contextView, R.string.error_add_message, Snackbar.LENGTH_SHORT)
//                .setAnchorView(binding.floatingActionButton)
//                .show()
//            //reset record added status
//            mChargingStationViewModel.setOnAdd(false)
//        }
//    }

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

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

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
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onActionExpandListener(menu: Menu) =
        object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                // Hide the other menu items when the search view is expanded
                val profileItem = menu.findItem(R.id.action_view_user_info)
                profileItem.isVisible = false
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                // Show the other menu items when the search view is collapsed
                val profileItem = menu.findItem(R.id.action_view_user_info)
                profileItem.isVisible = true
                return true
            }
        }

}