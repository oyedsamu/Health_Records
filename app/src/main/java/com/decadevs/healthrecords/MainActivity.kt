package com.decadevs.healthrecords

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.decadevs.healthrecords.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_HealthRecords)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(view)


    }


}