package com.example.goev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.goev.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Setup the bottom navigation bar as well as initialize navController to be used by fragments
        initialSetup()

    }

    private fun initialSetup() {
        initializeNavController()

        //setup bottom navigation bar
        setupBottomNavMenu(navController)
    }

    private fun initializeNavController() {
        //get an instance of navController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
    }

    //Used by fragments to setup top app bar
    fun setupActionBar(toolBar: MaterialToolbar) {

        setSupportActionBar(toolBar)

        // appBarConfiguration needed to initialize app bar
        appBarConfiguration = AppBarConfiguration(setOf(R.id.chargingStationLocatorMapFragment, R.id.chargingStationLocatorListFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        setupLogo(navController)
    }



    // Hide the logo when navigating to any fragment other than the home fragment
    private fun setupLogo(navController: NavController) {
        val topLevelDestinations = listOf(R.id.chargingStationLocatorMapFragment, R.id.chargingStationLocatorListFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in topLevelDestinations) {
                // Setup the logo
                supportActionBar?.setLogo(R.drawable.ic_go_ev)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            } else {
                supportActionBar?.setDisplayShowHomeEnabled(false)
                supportActionBar?.setLogo(null)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)
        //Navigates to the top-level of the following navigation icon selected
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.chargingStationLocatorMapFragment -> {
                    // Respond to navigation item 1 click
                    navController.navigate(R.id.chargingStationLocatorMapFragment)
//                    Log.i("MainActivity", "charging station map icon selected")
                    true
                }
                R.id.chargingStationLocatorListFragment -> {
                    // Respond to navigation item 2 click
                    navController.navigate(R.id.chargingStationLocatorListFragment)
//                    Log.i("MainActivity", "charging station list icon selected")
                    true
                }
                else -> {
                    Toast.makeText(this, "No such navigation item", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.locator_action_bar_menu, menu)
        return true
    }
}