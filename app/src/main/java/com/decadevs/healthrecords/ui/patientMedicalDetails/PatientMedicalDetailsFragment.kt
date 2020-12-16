package com.decadevs.healthrecords.ui.patientMedicalDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentPatientMedicalDetailsBinding

class PatientMedicalDetailsFragment : Fragment() {

    private var _binding: FragmentPatientMedicalDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientMedicalDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** FILL VIEWS WITH PATIENT DETAILS */
        val patientName = arguments?.getString("patientName")
        val practitioner = arguments?.getString("practitioner")
        val date = arguments?.getString("date")
        val details = arguments?.getString("details")

        binding.patientName.text = patientName
        binding.practitioner.text = practitioner
        binding.date.text = date
        binding.details.text = details

        /** BACK BUTTON */
        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.patientDetailsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}