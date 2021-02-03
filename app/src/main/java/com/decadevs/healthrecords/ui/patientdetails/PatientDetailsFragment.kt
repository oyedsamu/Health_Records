package com.decadevs.healthrecords.ui.patientdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.adapters.OnItemClick
import com.decadevs.healthrecords.adapters.PatientDetailsRVAdapter
import com.decadevs.healthrecords.data.PatientDetails
import com.decadevs.healthrecords.databinding.FragmentPatientDetailsBinding

class PatientDetailsFragment : Fragment(), OnItemClick {

    private var _binding: FragmentPatientDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** BACK BUTTON */
//        binding.backArrow.setOnClickListener {
//            findNavController().navigate(R.id.doctorPageFragment)
//        }

        binding.addRecord.setOnClickListener {
            findNavController().navigate(R.id.doctorPrescriptionFragment)
        }

        /** SET UP SPINNER */
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.years,
            R.layout.spinner_text
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.yearsSpinner.adapter = adapter
        }

        /** POPULATE PATIENT DETAILS RECYCLER VIEW WITH DUMMY DATA */
        val adapter = PatientDetailsRVAdapter(patientsDetails, this)
        val patientsDetailsRV = binding.patientDetailsList
        patientsDetailsRV.adapter = adapter
        patientsDetailsRV.layoutManager = LinearLayoutManager(this.context)
        patientsDetailsRV.setHasFixedSize(true)
    }

    private val patientsDetails = listOf<PatientDetails>(
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        PatientDetails("Dr Ben Ani", "24 FEB 2020", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
    )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(item: PatientDetails, position: Int) {
        var bundle = bundleOf(
            "patientName" to binding.patientName.text.toString(),
            "hospital" to binding.hospitalAddress.text.toString(),
            "practitioner" to item.practitioner,
            "date" to item.date,
            "details" to item.details
        )

        findNavController().navigate(R.id.patientMedicalDetailsFragment, bundle)
    }
}