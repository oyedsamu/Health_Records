package com.decadevs.healthrecords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.decadevs.healthrecords.ui.doctorpage.DoctorPageFragment
import com.decadevs.healthrecords.ui.patientdetails.PatientDetailsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_HealthRecords)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.framelayout, DoctorPageFragment())
//                .commit()
//        }
    }
}