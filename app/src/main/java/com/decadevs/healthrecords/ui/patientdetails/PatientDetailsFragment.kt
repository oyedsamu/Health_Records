package com.decadevs.healthrecords.ui.patientdetails


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
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
import com.decadevs.utils.*
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_patient_details.*
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

    private lateinit var patientName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPatientDetailsBinding.inflate(inflater, container, false)

        val repository = HealthRecordsRepositoryImpl(apiService)
        val factory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)

        patientName = "${args.patientData?.firstName} ${args.patientData?.lastName}"

        /** Retrieve patient's all records from api */
        args.patientData?.registrationNumber?.let { getPatientAllRecords(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** SHOW PATIENT ITEMS IN SIDE NAV */
        val navigationView: NavigationView = requireActivity().findViewById(R.id.nav_drawer)
        val menu = navigationView.menu
        showPatientMenuItems(menu)

        /** SAVE CURRENT PATIENT PERSONAL DETAILS */
        patientBloodGroup = args.patientData!!.bloodGroup
        patientGenotype = args.patientData!!.genoType
        patientAllergies = args.patientData!!.allergies
        patientDisabilities = args.patientData!!.disability

        val action = PatientDetailsFragmentDirections.actionPatientDetailsFragmentToVitalInfoFragment(
                args.patientData!!.bloodGroup,
                args.patientData!!.genoType,
                args.patientData!!.allergies,
                args.patientData!!.disability,
                args.patientData!!.registrationNumber,
                patientName
            )

        binding.vitalInfoBtn.setOnClickListener {
            findNavController().navigate(action)
        }

        updateFragmentUIWithPatientDataFromArgs()

        observePatientAllRecordsData()

        /** ADD PATIENT RECORD */
        binding.addRecord.setOnClickListener {
            when(roleName) {
                "Doctor" -> findNavController().navigate(R.id.doctorPrescriptionFragment)
                else -> findNavController().navigate(R.id.nurseComments)
            }
        }

        /** HIDE/SHOW VITAL INFO BUTTON */
        when(roleName) {
            "Doctor" -> binding.vitalInfoBtn.visibility = View.VISIBLE
            else -> binding.vitalInfoBtn.visibility = View.GONE
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
//        binding.patientDetailsBackIb.setOnClickListener {
//            findNavController().popBackStack()
//        }
    }

    override fun onResume() {
        super.onResume()
        val registrationNumber = SessionManager.load(requireContext(), "REGISTRATION-NUMBER")
        val patientName = SessionManager.load(requireContext(), "PATIENT-NAME")
        val hospitalAddress = SessionManager.load(requireContext(), "LOCATION")
       getPatientAllRecords(registrationNumber)

        binding.patientName.text = patientName
        binding.hospitalAddress.text = hospitalAddress
        binding.patientHospitalNum.text = registrationNumber
        Toast.makeText(requireContext(), "called", Toast.LENGTH_LONG).show()
    }



    private fun updateFragmentUIWithPatientDataFromArgs() {
        val patientAddress =
            "${args.patientData?.street}, ${args.patientData?.city}, ${args.patientData?.state}"

        binding.patientName.text = patientName
        binding.hospitalAddress.text = patientAddress
        binding.patientHospitalNum.text = args.patientData?.registrationNumber
    }

    private fun observePatientAllRecordsData() {
        viewModel.getAllPatientMedicalRecord.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    patientRecordsResponseList = it.value.data
                    Log.d("TAG", "Data success: $patientRecordsResponseList")

                    /** POPULATE PATIENT DETAILS RECYCLER VIEW WITH DATA */

                    binding.patientDetailsProgressBarPb.visibility = View.GONE
                    binding.patientDetailsFetchingDataTv.visibility = View.GONE

                    val adapter = PatientDetailsRVAdapter(patientRecordsResponseList, this)
                    val patientsDetailsRV = binding.patientDetailsList
                    patientsDetailsRV.adapter = adapter
                    patientsDetailsRV.layoutManager = LinearLayoutManager(this.context)
                    patientsDetailsRV.setHasFixedSize(true)

                    changeDisplayedData()
                }

                is Resource.Failure -> {
                    binding.patientDetailsProgressBarPb.visibility = View.GONE
                    binding.patientDetailsFetchingDataTv.visibility = View.GONE

                    showToast(
                        "Something went wrong retrieving patient's record.",
                        requireActivity()
                    )
                    Log.i("Records Failure", "${it.errorBody}, ${it.isNetworkError}")

                }
            }

        }
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
            "doctorNote" to item.doctorNotes,
            "diagnosis" to item.diagnosis,
            "isSensitive" to item.isSensitive,
            "prescription" to item.prescription,
            "recordId" to item.id,
            "patientRegNum" to args.patientData!!.registrationNumber
        )

        findNavController().navigate(R.id.patientMedicalDetailsFragment, bundle)
    }

    private fun getPatientAllRecords(patientId: String) {
        viewModel.getPatientAllRecords(patientId)
    }

    private fun filterPatientRecord(year: String): ArrayList<PatientRecordDataResponse> {
        val records: ArrayList<PatientRecordDataResponse> = arrayListOf()
        for(record in patientRecordsResponseList) {
//            Log.d("Pos", record.createdAt.substring(0..3))
            if(record.createdAt.substring(0..3) == year) {
                records.add(record)
            }
        }
        return records
    }

    private fun changeDisplayedData() {
        binding.yearsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            /** HANDLE SPINNER ITEM CHANGE */
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                /** FILTER LIST FOR SELECTED YEAR */
                val selectedYear = years_spinner.selectedItem.toString()
                val list = filterPatientRecord(selectedYear)
//                Log.d("Pos", list.toString())

                /** SHOW LIST OF FILTERED ITEMS */
                if(list.isEmpty()) {
                    binding.patientDetailsNoDataTv.visibility = View.VISIBLE
                    binding.patientDetailsList.visibility = View.INVISIBLE
                } else {
                    binding.patientDetailsNoDataTv.visibility = View.INVISIBLE
                    binding.patientDetailsList.visibility = View.VISIBLE

                    val adapter = PatientDetailsRVAdapter(list, this@PatientDetailsFragment)
                    val patientsDetailsRV = binding.patientDetailsList
                    patientsDetailsRV.adapter = adapter
                    patientsDetailsRV.layoutManager = LinearLayoutManager(requireContext())
                    patientsDetailsRV.setHasFixedSize(true)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}