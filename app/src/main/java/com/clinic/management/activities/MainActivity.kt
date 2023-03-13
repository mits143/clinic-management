package com.clinic.management.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.clinic.management.R
import com.clinic.management.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    lateinit var appBarConfiguration: AppBarConfiguration

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_search,
                R.id.nav_home,
                R.id.nav_my_appointment,
                R.id.nav_profile,
            )
        )
        binding.bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
        getPermission()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.nav_appointment -> hideBottomNavigation()
            R.id.nav_appointment_confirmed -> hideBottomNavigation()
            R.id.nav_dr_detail -> hideBottomNavigation()
            R.id.nav_category_listing -> hideBottomNavigation()
            R.id.nav_dr_listing -> hideBottomNavigation()
            R.id.nav_my_result -> hideBottomNavigation()
            R.id.nav_medicine -> hideBottomNavigation()
            R.id.nav_medicine_detail -> hideBottomNavigation()
            R.id.nav_doctor -> hideBottomNavigation()
            R.id.nav_dashbaord -> hideBottomNavigation()
            R.id.nav_faq -> hideBottomNavigation()
            R.id.nav_doctor_result -> hideBottomNavigation()
            R.id.nav_lab_radio_result -> hideBottomNavigation()
            R.id.nav_filter -> hideBottomNavigation()
            else -> showBottomNavigationView()
        }
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        binding.bottomNavView.visibility = View.GONE
    }

    private fun showBottomNavigationView() {
        binding.bottomNavView.visibility = View.VISIBLE
    }

//    override fun onBackPressed() {
//        val seletedItemId = binding.bottomNavView.selectedItemId
//        if (R.id.nav_home != seletedItemId) {
//            binding.bottomNavView.selectedItemId = R.id.nav_home
//        } else {
//            super.onBackPressed()
//        }
//    }

    fun openSearch() {
        binding.bottomNavView.selectedItemId = R.id.nav_search
    }
}