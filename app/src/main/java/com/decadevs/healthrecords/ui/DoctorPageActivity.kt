package com.decadevs.healthrecords.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.decadevs.healthrecords.MainActivity
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.ActivityDoctorPageBinding
import com.decadevs.healthrecords.ui.doctorpage.DoctorPageFragmentDirections
import com.decadevs.healthrecords.ui.patientdetails.PatientDetailsFragmentDirections
import com.decadevs.utils.*
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorPageActivity : AppCompatActivity (), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityDoctorPageBinding
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoctorPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /**Initialize Drawer Menu Listener**/
        val navigationView: NavigationView = findViewById(R.id.nav_drawer)
        navigationView.setNavigationItemSelectedListener(this)

        drawerLayout = binding.drawer


        binding.activityDoctorHambugerIv.setOnClickListener {
            binding.drawer.openDrawer(binding.navDrawer)
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.homeFragment -> {
                findNavController(R.id.activity_doctor_nav_host).navigate(R.id.doctorPageFragment)
            }

            R.id.vitalInfoFragment -> {
                val bundle = bundleOf(
                    "patientId" to currentPatientId,
                    "bloodGroup" to patientBloodGroup,
                    "genotype" to patientGenotype,
                    "allergies" to patientAllergies,
                    "disabilities" to patientDisabilities
                )
                findNavController(R.id.activity_doctor_nav_host).navigate(R.id.vitalInfoFragment, bundle)
            }

            R.id.historyFragment -> {
                Log.d("patientData", patientData.toString())
                val action = DoctorPageFragmentDirections.actionDoctorPageFragmentToPatientDetailsFragment(patientData)
                findNavController(R.id.activity_doctor_nav_host).navigate(action)

//                val bundle = bundleOf(
//
//                )
//                findNavController(R.id.activity_doctor_nav_host).navigate(R.id.patientDetailsFragment)
            }

            R.id.addHistoryFragment -> {
                findNavController(R.id.activity_doctor_nav_host).navigate(R.id.doctorPrescriptionFragment)
            }

            R.id.logout -> {

                val dialogInterface = DialogInterface.OnClickListener { dialog, _ ->

                    SessionManager.save(this, TOKEN, "")

                    Intent(this, MainActivity::class.java).also {
                        SessionManager.save(this, LOG_OUT, "true")

                        startActivity(it)
                        finish()
                    }

                    dialog.cancel()
                }
                showAlertDialog("Are you sure you want to log out?", "Log Out", dialogInterface)
            }
        }

        binding.drawer.closeDrawer(GravityCompat.START)
        return true

    }

    override fun onBackPressed() {
        super.onBackPressed()
        //this.finishAffinity()
        //this.moveTaskToBack(true)
    }
}