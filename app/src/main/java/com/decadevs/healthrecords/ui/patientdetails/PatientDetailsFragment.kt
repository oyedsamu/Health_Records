package com.decadevs.healthrecords.ui.patientdetails


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.adapters.OnItemClick
import com.decadevs.healthrecords.adapters.PatientDetailsRVAdapter
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentPatientDetailsBinding
import com.decadevs.healthrecords.model.response.PatientRecordDataResponse
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import com.decadevs.utils.currentPatientId
import com.decadevs.utils.patientIsInView
import com.decadevs.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PatientDetailsFragment : Fragment(), OnItemClick {

    private var _binding: FragmentPatientDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService

    private lateinit var viewModel: HealthRecordsViewModel

    private val args by navArgs<PatientDetailsFragmentArgs>()

    var patientRecordsResponseList = ArrayList<PatientRecordDataResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientDetailsBinding.inflate(inflater, container, false)

        val repository = HealthRecordsRepositoryImpl(apiService)
        val factory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)

        /** Retrieve patient's all records from api */
        getPatientAllRecords(args.patientData.registrationNumber)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** SET CURRENT PATIENT DETAILS TO BE SHOWN ON SIDE NAV BAR */
        patientIsInView = true
        currentPatientId = binding.patientHospitalNum.text.toString()

        updateFragmentUIWithPatientDataFromArgs()

        observePatientAllRecordsData()

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

        /** HANDLE BACK BUTTON */
        binding.patientDetailsBackIb.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateFragmentUIWithPatientDataFromArgs() {
        val patientName = "${args.patientData.firstName} ${args.patientData.lastName}"
        val patientAddress =
            "${args.patientData.street}, ${args.patientData.city}, ${args.patientData.state}"

        binding.patientName.text = patientName
        binding.hospitalAddress.text = patientAddress
        binding.patientHospitalNum.text = args.patientData.registrationNumber
    }

    private fun observePatientAllRecordsData() {
        viewModel.getAllPatientMedicalRecord.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    patientRecordsResponseList = it.value.data
                    Log.d("TAG", "Data success: $patientRecordsResponseList")

                    /** POPULATE PATIENT DETAILS RECYCLER VIEW WITH DUMMY DATA */
                    val adapter = PatientDetailsRVAdapter(patientRecordsResponseList, this)
                    val patientsDetailsRV = binding.patientDetailsList
                    patientsDetailsRV.adapter = adapter
                    patientsDetailsRV.layoutManager = LinearLayoutManager(this.context)
                    patientsDetailsRV.setHasFixedSize(true)
                }

                is Resource.Failure -> {
                    showToast(
                        "Something went wrong retrieving patient's record.",
                        requireActivity()
                    )
                    Log.i("Records Failure", "${it.errorBody}, ${it.isNetworkError}")

                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(item: PatientRecordDataResponse, position: Int) {
        var bundle = bundleOf(
            "patientName" to binding.patientName.text.toString(),
            "hospital" to binding.hospitalAddress.text.toString(),
            "practitioner" to item.doctorOnCall,
            "date" to item.createdAt,
            "details" to item.doctorNotes
        )

        findNavController().navigate(R.id.patientMedicalDetailsFragment, bundle)
    }

    private fun getPatientAllRecords(patientId: String) {
        viewModel.getPatientAllRecords(patientId)
    }
}