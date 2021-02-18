package com.decadevs.healthrecords.ui.patientMedicalDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.databinding.FragmentPatientMedicalDetailsBinding
import com.google.android.material.textfield.TextInputLayout

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

        updateInputsFieldsWithAvailableData()

        /** BACK BUTTON */
        binding.medicalDetailsBackIb.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentDoctorPrescriptionBtn.setOnClickListener {




        }
    }


    private fun updateInputsFieldsWithAvailableData () {

        /** FILL VIEWS WITH PATIENT DETAILS */
        val patientName = arguments?.getString("patientName")
        val practitioner = arguments?.getString("practitioner")
        val date = arguments?.getString("date")
        val hospital = arguments?.getString("hospital")
        val patientDiagnosis = arguments?.getString("diagnosis")
        val doctorNote = arguments?.getString("doctorNote")
        val isSensitive = arguments?.getString("isSensitive")
        val prescription = arguments?.getString("prescription")
        val recordId = arguments?.getString("recordId")

        // setting it to text
        binding.patientName.text = patientName
        binding.practitioner.text = practitioner
        binding.hospitalName.text = hospital
        binding.date.text = date
        binding.patientDiagnosisEt.setText(patientDiagnosis)
        binding.fragmentDoctorNoteTextInputEt.setText(doctorNote)
        binding.fragmentPatientPrescriptionEditText.setText(prescription)
        binding.fragmentPatientTypeDropdown.setText(isSensitive)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}