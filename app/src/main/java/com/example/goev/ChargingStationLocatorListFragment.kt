package com.example.goev

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.goev.databinding.FragmentChargingStationLocatorListBinding
import com.example.goev.databinding.FragmentChargingStationLocatorMapBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChargingStationLocatorListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChargingStationLocatorListFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    private var _binding: FragmentChargingStationLocatorListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_charging_station_locator_list, container, false)
        _binding = FragmentChargingStationLocatorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setupActionBar(binding.topAppBar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_view_list).isVisible = false
        menu.findItem(R.id.action_view_map).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Navigate to settings screen.
                Log.i("ChargingLocatorList", "search action triggered")
                Toast.makeText(requireContext(), "Search selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_view_map -> {
                Log.i("ChargingLocatorList", "Map view selected")
                navigateToMapFragment()
                true
            }
            R.id.userInfo -> {
                // Save profile changes.
                Log.i("ChargingLocatorList", "userInfo in fragment called")
                Toast.makeText(requireContext(), "User profile selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToMapFragment() {
        val action = ChargingStationLocatorListFragmentDirections.actionChargingStationLocatorListFragmentToChargingStationLocatorMapFragment()
        view?.findNavController()?.navigate(action)
    }


//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ChargingStationLocatorListFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ChargingStationLocatorListFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}