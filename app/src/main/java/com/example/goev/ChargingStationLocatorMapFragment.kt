package com.example.goev

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.goev.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChargingStationLocatorMapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChargingStationLocatorMapFragment : Fragment(), MenuHost{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charging_station_locator_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonView = view?.findViewById<Button>(R.id.buttonTest)
        buttonView?.setOnClickListener {
            val action = ChargingStationLocatorMapFragmentDirections.actionChargingStationLocatorMapFragmentToChargingStationLocatorListFragment()
            view?.findNavController()?.navigate(action)
        }

        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                val myMenuItem = menu.findItem(R.id.action_search)
//                myMenuItem.isVisible = findNavController().currentDestination?.id == R.id.chargingStationLocatorMapFragment
                menu.add(Menu.NONE, R.id.action_search, Menu.FIRST, "Search")
//                Log.i("ChargingStation", "${menu.findItem(R.id.userInfo).order}")
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.action_search -> {
                        Toast.makeText(requireActivity(), "Search action selected.", Toast.LENGTH_SHORT).show()
                        true
                    }
                }
                return false
            }
        }, viewLifecycleOwner)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
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

    override fun addMenuProvider(provider: MenuProvider) {
        TODO("Not yet implemented")
    }

    override fun addMenuProvider(provider: MenuProvider, owner: LifecycleOwner) {
        TODO("Not yet implemented")
    }


    override fun addMenuProvider(
        provider: MenuProvider,
        owner: LifecycleOwner,
        state: Lifecycle.State
    ) {
        TODO("Not yet implemented")
    }

    override fun removeMenuProvider(provider: MenuProvider) {
        TODO("Not yet implemented")
    }

    override fun invalidateMenu() {
        TODO("Not yet implemented")
    }
}