package com.decadevs.healthrecords.ui.vitalinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentPatientMedicalDetailsBinding
import com.decadevs.healthrecords.databinding.FragmentVitalInfoBinding


class VitalInfoFragment : Fragment() {

    private var _binding: FragmentVitalInfoBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<VitalInfoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVitalInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentVitalInfoIdTv.text = args.patientId
        binding.fragmentVitalInfoPositiveTv.text = args.bloodGroup
        binding.fragmentVitalInfoAATv.text = args.genotype
        binding.fragmentVitalInfoFoodTv.text = args.allergies
        binding.fragmentVitalInfoDisabilitiesBloodTv.text = args.disabilities

        /** BACK BUTTON */
        binding.vitalInfoBackIb.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}