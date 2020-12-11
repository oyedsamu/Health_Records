package com.decadevs.healthrecords.ui.patientdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.adapters.PatientDetailsRVAdapter
import com.decadevs.healthrecords.data.PatientDetails
import com.decadevs.healthrecords.databinding.FragmentPatientDetailsBinding

class PatientDetailsFragment : Fragment() {

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
        /** SET UP SPINNER */
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.years,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.yearsSpinner.adapter = adapter
        }

        /** POPULATE PATIENT DETAILS RECYCLER VIEW WITH DUMMY DATA */
        val adapter = PatientDetailsRVAdapter(patientsDetails)
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
}