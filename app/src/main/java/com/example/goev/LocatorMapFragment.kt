package com.example.goev

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.goev.database.ChargingStationViewModel
import com.example.goev.databinding.FragmentLocatorMapBinding
import com.google.android.material.snackbar.Snackbar

class LocatorMapFragment : Fragment() {


    private var _binding: FragmentLocatorMapBinding? = null
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_locator_map, container, false)

        mChargingStationViewModel = ViewModelProvider(this).get(ChargingStationViewModel::class.java)

        return binding.root
//        return inflater.inflate(R.layout.fragment_charging_station_locator_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setupActionBar(binding.topAppBar)

//        val buttonView = view?.findViewById<Button>(R.id.buttonTest)
//        buttonView?.setOnClickListener {
//            val action = LocatorMapFragmentDirections.actionLocatorMapFragmentToLocatorListFragment()
//            view?.findNavController()?.navigate(action)
//        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.locator_action_bar_menu, menu)
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        // make sure the searchView takes up the whole app bar
        searchItem.setOnActionExpandListener(onActionExpandListener(menu))

        // Configure the search info and add any event listeners.
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Navigate to settings screen.
                Log.i("ChargingLocator", "search action triggered")

                Toast.makeText(requireContext(), "Search selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_view_user_info -> {
                // Save profile changes.
                Log.i("ChargingLocator", "userInfo in fragment called")
                Toast.makeText(requireContext(), "User profile selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



//    private fun navigateToListFragment() {
//        val action = LocatorMapFragmentDirections.actionLocatorMapFragmentToLocatorListFragment()
//        view?.findNavController()?.navigate(action)
//    }

//    private fun setupTopAppBarButtons(topAppBar: MaterialToolbar) {
//        topAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.userInfo -> {
//                    // Handle more item (inside overflow menu) press
//                    Toast.makeText(requireContext(), "User profile button is pressed.", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false
//            }
//        }
//    }

}