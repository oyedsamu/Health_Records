package com.decadevs.healthrecords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.decadevs.healthrecords.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setTheme(R.style.Theme_HealthRecords)


        /** Open navigation drawer onclick of hamburger menu icon */
        binding.productMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navigationView)
        }

        binding.productMenu.visibility = View.VISIBLE

        //find navigation view
        val navigationView: NavigationView = binding.navigationView

        //Set color tint to null
        navigationView.itemIconTintList = null

        //Implement navigation onclick of action menu item
        selectNavigationItem(navigationView)


        //Close drawer onclick of close icon
        binding.close.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

    }

    private fun selectNavigationItem(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {

            val findNavController = findNavController(R.id.nav_host_fragment)

            when (it.itemId) {
                R.id.actionHome -> {
                    findNavController.navigate(R.id.doctorPageFragment)
                }

                R.id.actionLogout -> {
                    finish()
                }

            }

            it.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}