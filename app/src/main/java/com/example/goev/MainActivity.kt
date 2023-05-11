package com.example.goev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.goev.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Setup the app bar and bottom navigation bar as well as logo
        initialSetup()

    }

    private fun initialSetup() {
        //get an instance of navController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // appBarConfiguration needed to initialize app bar
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Set the top app bar as the activity's action bar
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setupTopAppBarButtons(toolbar)

        setupLogo(navController)

        //setup bottom navigation bar
        setupBottomNavMenu(navController)
    }

    private fun setupTopAppBarButtons(topAppBar: MaterialToolbar) {
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.userInfo -> {
                    // Handle more item (inside overflow menu) press
//                    Toast.makeText(this, "User profile button is pressed.", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    // Hide the logo when navigating to any fragment other than the home fragment
    private fun setupLogo(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.chargingStationLocatorMapFragment) {
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
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }
}