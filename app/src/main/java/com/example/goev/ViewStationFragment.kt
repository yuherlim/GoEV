package com.example.goev

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.goev.databinding.FragmentViewStationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ViewStationFragment : Fragment() {

    private var _binding: FragmentViewStationBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ViewStationFragmentArgs>()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Update view station with the item passed from recyclerView
        binding.viewEvStationName.text = args.currentChargingStation.name
        binding.viewEvStationAddress.text = args.currentChargingStation.address
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
        inflater.inflate(R.menu.add_station_action_bar_menu, menu)
    }
}