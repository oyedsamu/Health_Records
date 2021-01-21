package com.decadevs.healthrecords.onBoardingScreens

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.OnBoardingFragmentTwo
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.adapters.IntroSliderAdapter
import com.decadevs.healthrecords.databinding.FragmentOnboardingScreenBinding
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingScreenFragment : Fragment(R.layout.fragment_onboarding_screen) {

    private val PREFS_NAME : String = "first_time"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOnboardingScreenBinding.bind(view)

        val fragmentList : ArrayList<Fragment> = arrayListOf(
            OnBoardingFragmentOne(),
            OnBoardingFragmentTwo(),
            OnBoardingFragmentThree()
        )

        //Connect the fragment list to the view pager
        val adapter = activity?.supportFragmentManager?.let {
            IntroSliderAdapter(
                fragmentList,
                it,
                lifecycle
            )
        }

        binding.fragmentOnBoardingVp.adapter = adapter

        //TabLayoutMediator to set indicator to the tab layout

        TabLayoutMediator(
            binding.fragmentOnBoardingIndicatorTl,
            binding.fragmentOnBoardingVp
        ) { tab, position ->
        }.attach()

        //Set Onclick listener to get started button and navigate to Sign up pager
        binding.fragmentOnBoardingGetStartedBtn.setOnClickListener {

           onBoardingFinished()
            // navigate to the home screen
            findNavController().navigate(R.id.loginFragment)

        }

    }

    override fun onStart() {
        super.onStart()

        if (loadFromSharedPreference(requireActivity())) {
            findNavController().navigate(R.id.loginFragment)
        }

    }

    private fun onBoardingFinished () {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("finished", true)
        editor.apply()
    }

    private fun loadFromSharedPreference(activity: Activity): Boolean {
        val sharedPreferences: SharedPreferences =
            activity.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finished", false)
    }

}