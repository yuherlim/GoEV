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
        appBarConfiguration = AppBarConfiguration(setOf(R.id.TrackerFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        setupLogo(navController)
    }



    // Hide the logo when navigating to any fragment other than the home fragment
    private fun setupLogo(navController: NavController) {
        val topLevelDestinations = listOf(R.id.TrackerFragment)
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
                R.id.TrackerFragment -> {
                    // Respond to navigation item 2 click
                    navController.navigate(R.id.TrackerFragment)
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
        menuInflater.inflate(R.menu.tracker_action_bar_menu, menu)
        return true
    }
}