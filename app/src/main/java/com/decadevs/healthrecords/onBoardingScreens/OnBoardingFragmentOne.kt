package com.decadevs.healthrecords.onBoardingScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentOnboardingOneBinding


class OnBoardingFragmentOne : Fragment(R.layout.fragment_onboarding_one) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOnboardingOneBinding.bind(view)



    }


}