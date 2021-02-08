package com.decadevs.healthrecords.ui.prescription

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentDoctorPrescriptionBinding
import com.decadevs.healthrecords.model.request.MedicalRecordRequest
import com.decadevs.healthrecords.repository.HealthRecordsRepository
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import com.decadevs.utils.FormValidator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorPrescriptionFragment : Fragment() {

    private var _binding : FragmentDoctorPrescriptionBinding? = null
    private val binding get() = _binding!!

    private var validator = FormValidator()
    private lateinit var patientsDiagnosis: String
    private lateinit var patientsPrescription: String
    private var type: Boolean = false
    private lateinit var doctorsNote: String
    private lateinit var patientRegistrationNumber: String

    @Inject lateinit var apiService: ApiService
    private lateinit var viewModel: HealthRecordsViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var repository: HealthRecordsRepositoryImpl


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoctorPrescriptionBinding.inflate(inflater, container, false)

        val items = listOf<String>("True", "False")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_dropdown, items)
        val dropDownTextFieldLayout = (binding.fragmentPatientTypeTextInputDropdownLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentDoctorPrescriptionBtn.setOnClickListener {
            findNavController().navigate(R.id.nurseComments)
        }

        /** SET UP VIEW-MODEL */
        repository = HealthRecordsRepositoryImpl(apiService)
        viewModelFactory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory).get(HealthRecordsViewModel::class.java)

        /** SUBMIT FORM */
        binding.fragmentDoctorPrescriptionBtn.setOnClickListener {
            /** GET FORM FIELDS DATA */
            getFormData()
            if(validateForm()) {
                /** SHOW PROGRESS BAR */
                binding.prescriptionProgressBarPb.visibility = View.VISIBLE
                /** SEND POST REQUEST TO ADD DOCTOR'S DIAGNOSIS */
                addDiagnosis()
                /** NAVIGATE TO PATIENT MEDICAL DETAILS SCREEN IF SUCCESSFUL */
//                findNavController().navigate(R.id.patientMedicalDetailsFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFormData() {
        binding.apply {
            patientsDiagnosis = patientDiagnosisEt.text.toString()
            patientsPrescription = fragmentPatientPrescriptionEditText.text.toString()
            type = fragmentPatientTypeDropdown.text.toString() == "True"
            doctorsNote = fragmentDoctorNoteTextInputEt.text.toString()
            patientRegistrationNumber = "23657E5"
        }
    }

    private fun validateForm(): Boolean {
        when {
            validator.checkIfEmpty(patientsDiagnosis) -> {
                Toast.makeText(context, "Patient's diagnosis cannot be blank", Toast.LENGTH_SHORT).show()
                return false
            }
            validator.checkIfEmpty(patientsPrescription) -> {
                Toast.makeText(context, "Patient's prescription cannot be blank", Toast.LENGTH_SHORT).show()
                return false
            }
            validator.checkIfEmpty(doctorsNote) -> {
                Toast.makeText(context, "Doctor's Note cannot be blank", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun addDiagnosis() {

    //   val recordRequest = MedicalRecordRequest(patientsDiagnosis, patientsPrescription, type, doctorsNote, patientRegistrationNumber, null, null )
        val recordRequest = MedicalRecordRequest("sfl", "asf", true, "sldfk", "23657E5", null, "slfk")
        //Toast.makeText(requireContext(), viewModel.token, Toast.LENGTH_SHORT).show()
        viewModel.addMedicalRecord(recordRequest)
        viewModel.medicalRecordResponse.observe(viewLifecycleOwner, Observer {
            //            Log.i("Record Response", "$it")
            when (it) {
                is Resource.Success -> {
                    Log.i("response", it.value.success.toString())
                    binding.prescriptionProgressBarPb.visibility = View.GONE
                    Toast.makeText(this.context, "Record Successful", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Log.i("response", it.errorBody.toString())
                    binding.prescriptionProgressBarPb.visibility = View.GONE
                    Toast.makeText(this.context, it.errorBody?.errorBody()?.string(), Toast.LENGTH_LONG ).show()
                   // Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}