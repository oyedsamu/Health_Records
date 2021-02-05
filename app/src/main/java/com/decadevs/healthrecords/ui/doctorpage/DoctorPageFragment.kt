package com.decadevs.healthrecords.ui.doctorpage

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentDoctorPageBinding
import com.decadevs.healthrecords.datastore.UserManager
import com.decadevs.healthrecords.model.response.PatientDataResponse
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import com.decadevs.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorPageFragment : Fragment() {
    private var _binding: FragmentDoctorPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService

    private var mProgressDialog: ProgressDialog? = null

    private lateinit var viewModel: HealthRecordsViewModel
    private lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDoctorPageBinding.inflate(inflater, container, false)

        val repository = HealthRecordsRepositoryImpl(apiService)
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)
        userManager = UserManager(requireActivity())

        getStaffIdFromDataStoreAndImplementApiCall()

        viewModel.getStaffResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    val successResponse = it.value.data
                    Log.i("Staff Response", successResponse.toString())
                    //Update UI
                    binding.doctorName.text =
                        successResponse.firstname + " " + successResponse.lastname
                    binding.doctorEmail.text = successResponse.email
                    binding.hospitalAddress.text = successResponse.healthcareProviderName
                    binding.doctorPhoneNumber.text = successResponse.phoneNumber
                }
                is Resource.Failure -> {
                    Log.i("Staff Response Failure", "${it.errorBody}, ${it.isNetworkError}")

                }
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {
//            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
            val isSearchFieldValidated = validateSearchField()

            if (isSearchFieldValidated) {
                mProgressDialog =
                    ProgressDialog.show(
                        requireActivity(),
                        "Fetching patient data",
                        "Please wait...",
                        false,
                        false
                    )

                getPatientData(binding.search.text.toString())
            }

            viewModel.getPatientData.observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Success -> {
                        //response
                        val res = it.value.data
                        val patientObject = PatientDataResponse(
                            res.id,
                            res.firstName,
                            res.lastName,
                            res.registrationNumber,
                            res.weight,
                            res.height,
                            res.gender,
                            res.doB,
                            res.state,
                            res.city,
                            res.street,
                            res.bloodGroup,
                            res.genoType,
                            res.allergies,
                            res.disability,
                            res.createdAt,
                            res.updatedAt
                        )

                        mProgressDialog!!.dismiss()

                        val action =
                            DoctorPageFragmentDirections.actionDoctorPageFragment2ToPatientDetailsFragment2(
                                patientObject
                            )

                        findNavController().navigate(action)
                        Log.d("TAG", "Data success: $res")

                    }

                    is Resource.Failure -> {
                        mProgressDialog!!.dismiss()
                        showToast("Something went wrong. Please try again", requireActivity())
                        Log.i("Records Failure", "${it.errorBody}, ${it.isNetworkError}")

                    }
                }

            })

        }

        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun getStaffIdFromDataStoreAndImplementApiCall() {
        userManager.rmUserIdFlow.asLiveData().observe(requireActivity(), { uid ->
            if (uid != "") {
                viewModel.getStaff(uid)
            }
        })
    }

    private fun getPatientData(patientId: String) {
        viewModel.getPatientData(patientId)
    }

    private fun validateSearchField(): Boolean {
        return if (binding.search.text.isEmpty()) {
            showToast("Please enter patient ID", requireActivity())
            false
        } else
            true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}