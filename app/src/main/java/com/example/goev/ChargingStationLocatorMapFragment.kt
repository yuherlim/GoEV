package com.example.goev

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.goev.databinding.FragmentChargingStationLocatorMapBinding
import com.google.android.material.appbar.MaterialToolbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChargingStationLocatorMapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class ChargingStationLocatorMapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentChargingStationLocatorMapBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChargingStationLocatorMapBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_charging_station_locator_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setupActionBar(binding.topAppBar)

        val buttonView = view?.findViewById<Button>(R.id.buttonTest)
        buttonView?.setOnClickListener {
            val action = ChargingStationLocatorMapFragmentDirections.actionChargingStationLocatorMapFragmentToChargingStationLocatorListFragment()
            view?.findNavController()?.navigate(action)
        }

//        val topAppBar = requireActivity().
//
//        topAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.edit -> {
//                    // Handle edit text press
//                    true
//                }
//                R.id.favorite -> {
//                    // Handle favorite icon press
//                    true
//                }
//                R.id.more -> {
//                    // Handle more item (inside overflow menu) press
//                    true
//                }
//                else -> false
//            }
//        }

//        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
////                val myMenuItem = menu.findItem(R.id.action_search)
////                myMenuItem.isVisible = findNavController().currentDestination?.id == R.id.chargingStationLocatorMapFragment
//                menu.add(Menu.NONE, R.id.action_search, Menu.FIRST, "Search")
////                Log.i("ChargingStation", "${menu.findItem(R.id.userInfo).order}")
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                when(menuItem.itemId) {
//                    R.id.action_search -> {
//                        Toast.makeText(requireActivity(), "Search action selected.", Toast.LENGTH_SHORT).show()
//                        true
//                    }
//                }
//                return false
//            }
//        }, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.locator_action_bar_menu, menu)
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Navigate to settings screen.
                Log.i("ChargingLocator", "search action triggered")
                Toast.makeText(requireContext(), "Search selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_view_list -> {
                Log.i("ChargingLocator", "List view selected")
                navigateToListFragment()
                true
            }
            R.id.userInfo -> {
                // Save profile changes.
                Log.i("ChargingLocator", "userInfo in fragment called")
                Toast.makeText(requireContext(), "User profile selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToListFragment() {
        val action = ChargingStationLocatorMapFragmentDirections.actionChargingStationLocatorMapFragmentToChargingStationLocatorListFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun setupTopAppBarButtons(topAppBar: MaterialToolbar) {
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.userInfo -> {
                    // Handle more item (inside overflow menu) press
                    Toast.makeText(requireContext(), "User profile button is pressed.", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChargingStationLocatorMapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChargingStationLocatorMapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}