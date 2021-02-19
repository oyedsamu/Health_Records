package com.decadevs.healthrecords.ui.prescription

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentDoctorPrescriptionBinding
import com.decadevs.healthrecords.model.response.FormData
import com.decadevs.healthrecords.model.response.MedicalRecordResponse
import com.decadevs.healthrecords.model.response.PatientDataResponse
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import com.decadevs.utils.FormValidator
import dagger.hilt.android.AndroidEntryPoint
import java.net.URI
import java.util.jar.Manifest
import javax.inject.Inject

private const val REQUEST_CODE = 0

@AndroidEntryPoint
class DoctorPrescriptionFragment : Fragment() {


    private var _binding: FragmentDoctorPrescriptionBinding? = null
    private val binding get() = _binding!!

    private var validator = FormValidator()
    private lateinit var patientsDiagnosis: String
    private lateinit var patientsPrescription: String
    private var type: Boolean = false
    private lateinit var doctorsNote: String
    private lateinit var patientRegistrationNumber: String
    private lateinit var descriptionOfFileUploaded : String

    @Inject
    lateinit var apiService: ApiService
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
        val dropDownTextFieldLayout =
            (binding.fragmentPatientTypeTextInputDropdownLayout.editText as? AutoCompleteTextView)?.setAdapter(
                adapter
            )
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

        addDiagnosis()

        /** SUBMIT FORM */
        binding.fragmentDoctorPrescriptionBtn.setOnClickListener {
            /** GET FORM FIELDS DATA */
            getFormData()
            if (validateForm()) {
                /** SHOW PROGRESS BAR */
                binding.prescriptionProgressBarPb.visibility = View.VISIBLE
                /** SEND POST REQUEST TO ADD DOCTOR'S DIAGNOSIS */

                viewModel.addMedicalRecord(
                    requireActivity(),
                    binding.patientDiagnosisEt.text.toString(),
                    binding.fragmentPatientPrescriptionEditText.text.toString(),
                    true,
                    binding.fragmentDoctorNoteTextInputEt.text.toString(),
                    binding.fragmentPatientRegNumberEditText.text.toString(),
                    binding.fragmentDoctorPrescriptionFileUploadIv.toString(),
                    binding.fragmentPatientPrescriptionDescriptionTv.text.toString()
                )
                /** NAVIGATE TO PATIENT MEDICAL DETAILS SCREEN IF SUCCESSFUL */
//                findNavController().navigate(R.id.patientMedicalDetailsFragment)
            }

        }


        binding.fragmentDoctorPrescriptionUploadFileBtn.setOnClickListener {
            checkForPermissionAndUpLoadFile()
        }

//        binding.doctorPrescriptionBackIb.setOnClickListener {
//            findNavController().popBackStack()
//        }
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
                Toast.makeText(context, "Patient's diagnosis cannot be blank", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            validator.checkIfEmpty(patientsPrescription) -> {
                Toast.makeText(
                    context,
                    "Patient's prescription cannot be blank",
                    Toast.LENGTH_SHORT
                ).show()
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

        viewModel.medicalRecordResponse.observe(viewLifecycleOwner, Observer {
//                        Log.i("Record Response", "$it")
            when (it) {
                is Resource.Success -> {
                    Log.i("response", it.value.success.toString())
                    binding.prescriptionProgressBarPb.visibility = View.GONE
                    Toast.makeText(this.context, "Record Successful", Toast.LENGTH_SHORT).show()

                    findNavController().popBackStack()
                }
                is Resource.Failure -> {
                    Log.i("response", it.errorBody.toString())
                    binding.prescriptionProgressBarPb.visibility = View.GONE
                    Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }





    private fun checkForPermissionAndUpLoadFile () {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) &&
                    (checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)) {
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionCoarse = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE_WRITE)
                requestPermissions(permissionCoarse, PERMISSION_CODE_READ)

            } else {
                chooseImageGallery ()
            }
        }
    }


    private fun  chooseImageGallery () {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, IMAGE_CHOOSE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE_READ -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                } else {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK){
            if (data != null) {
                binding.fragmentDoctorPrescriptionFileUploadIv.setImageURI(data?.data)
            }
        }
    }

    // companion object
    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE_WRITE = 1001;
        private val PERMISSION_CODE_READ = 1002;

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

