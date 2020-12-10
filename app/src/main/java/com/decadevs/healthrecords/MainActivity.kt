package com.decadevs.healthrecords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.decadevs.healthrecords.ui.doctorpage.DoctorPageFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_HealthRecords)
        setContentView(R.layout.activity_main)

    }
}