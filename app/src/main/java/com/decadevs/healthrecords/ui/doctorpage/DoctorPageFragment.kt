package com.decadevs.healthrecords.ui.doctorpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentDoctorPageBinding
import com.decadevs.healthrecords.datastore.UserManager
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class DoctorPageFragment : Fragment() {
    private var _binding: FragmentDoctorPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService

    private lateinit var viewModel: HealthRecordsViewModel
    private lateinit var userManager: UserManager

    override fun onStart() {
        super.onStart()

    }

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



        binding.searchBtn.setOnClickListener {
//            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
//            getStaffIdFromDataStoreAndImplementApiCall()
            findNavController().navigate(R.id.patientDetailsFragment)
        }

        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        return binding.root
    }

    private fun getStaffIdFromDataStoreAndImplementApiCall() {
        userManager.rmUserIdFlow.asLiveData().observe(requireActivity(), { uid ->
            if (uid != "") {
                viewModel.getStaff(uid)
            }
        })


    }

    override fun onResume() {
        super.onResume()

        viewModel.getStaffResponse.observe(viewLifecycleOwner, {
            Log.i("Get staff Response ", "$it")

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


                    findNavController().navigate(R.id.patientDetailsFragment)


                }
                is Resource.Failure -> {
                    Log.i("Staff Response Failure", "${it.errorBody}, ${it.isNetworkError}")

                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}