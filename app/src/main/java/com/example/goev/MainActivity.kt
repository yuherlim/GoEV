package com.example.goev

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.goev.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val topLevelDestinations = setOf(R.id.trackerFragment, R.id.settingsFragment, R.id.tipsAndKnowledge)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Setup the bottom navigation bar as well as initialize navController to be used by fragments
        initialSetup()

    }

    private fun initialSetup() {
        initializeNavController()

        setupActionBar(binding.topAppBar)

        //setup bottom navigation bar
        setupBottomNavMenu(navController)
    }

    private fun initializeNavController() {
        //get an instance of navController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
    }


    private fun setupActionBar(toolBar: MaterialToolbar) {

        setSupportActionBar(toolBar)

        // appBarConfiguration needed to initialize app bar
        appBarConfiguration = AppBarConfiguration(topLevelDestinations)

        setupActionBarWithNavController(navController, appBarConfiguration)

        setupLogo(navController)
    }


    // Hide the logo when navigating to any fragment other than the top level destination fragments
    private fun setupLogo(navController: NavController) {

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
        bottomNav.labelVisibilityMode = LABEL_VISIBILITY_LABELED
        bottomNav.setupWithNavController(navController)
        //Navigates to the top-level of the following navigation icon selected
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.trackerFragment -> {
                    navController.navigate(R.id.trackerFragment)
                    true
                }
                R.id.tipsAndKnowledge -> {
                    navController.navigate(R.id.tipsAndKnowledge)
                    true
                }
//                R.id.forumFragment -> {
//                    navController.navigate(R.id.forumFragment)
//                    true
//                }
                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment)
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

    fun hideTopAppBar() {
        supportActionBar?.hide()
    }

    fun showTopAppBar() {
        supportActionBar?.show()
    }
}