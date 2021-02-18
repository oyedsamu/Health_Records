package com.decadevs.healthrecords.ui.patientMedicalDetails

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentPatientMedicalDetailsBinding
import com.decadevs.healthrecords.model.request.RecordUpdateRequest
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import com.decadevs.utils.showToast
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PatientMedicalDetailsFragment : Fragment() {

    private var _binding: FragmentPatientMedicalDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService
    private lateinit var viewModel: HealthRecordsViewModel

    private lateinit var patientDiagnosisField: TextInputLayout
    private lateinit var prescriptionField: TextInputLayout
    private lateinit var doctorNoteField: TextInputLayout

    private var mProgressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientMedicalDetailsBinding.inflate(inflater, container, false)
        patientDiagnosisField = binding.fragmentPatientDiagnosisInputLayout
        prescriptionField = binding.fragmentPatientPrescriptionLayout
        doctorNoteField = binding.fragmentDoctorNoteTextInputLayout

        val repository = HealthRecordsRepositoryImpl(apiService)
        val factory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)


        viewModel.updateMedicalRecord.observe(viewLifecycleOwner, {
            mProgressDialog!!.dismiss()
            when (it) {
                is Resource.Success -> {
                    showToast(
                        "Medical Record Updated successfully",
                        requireActivity()
                    )
                }

                is Resource.Failure -> {
                    Log.d("TAG", "updateRecordFailed: ${it.errorBody}")
                    showToast(
                        "Medical Record Update Failed. You may not be authorized",
                        requireActivity()
                    )
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateInputsFieldsWithAvailableData()

        val recordId = arguments?.getString("recordId")
        val patientRegNum = arguments?.getString("patientRegNum")

        /** BACK BUTTON */
        binding.medicalDetailsBackIb.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentDoctorPrescriptionBtn.setOnClickListener {

            when {
                patientDiagnosisField.editText?.text?.trim().toString().isEmpty() -> {
                    patientDiagnosisField.error = "Please enter a record for the patient"
                }
                prescriptionField.editText?.text?.trim().toString().isEmpty() -> {
                    prescriptionField.error = "Please enter a record for the patient"
                }
                doctorNoteField.editText?.text?.trim().toString().isEmpty() -> {
                    doctorNoteField.error = "Please enter a record for the patient"
                }
                else -> {
                    mProgressDialog =
                        ProgressDialog.show(
                            requireActivity(),
                            "Updating Patient's Medical Record",
                            "Please wait...",
                            false,
                            false
                        )

                    val medicalUpdateRequest = RecordUpdateRequest(
                        recordId!!,
                        patientDiagnosisField.editText?.text?.trim().toString(),
                        prescriptionField.editText?.text?.trim().toString(),
                        true,
                        doctorNoteField.editText?.text?.trim().toString()
                    )
                    viewModel.updateMedicalRecord(patientRegNum!!, medicalUpdateRequest)
                }
            }


        }
    }


    private fun updateInputsFieldsWithAvailableData() {

        /** FILL VIEWS WITH PATIENT DETAILS */
        val patientName = arguments?.getString("patientName")
        val practitioner = arguments?.getString("practitioner")
        val date = arguments?.getString("date")
        val hospital = arguments?.getString("hospital")
        val patientDiagnosis = arguments?.getString("diagnosis")
        val doctorNote = arguments?.getString("doctorNote")
        val isSensitive = arguments?.getString("isSensitive")
        val prescription = arguments?.getString("prescription")

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