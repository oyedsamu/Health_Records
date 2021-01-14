package com.decadevs.healthrecords.ui.doctorpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.databinding.FragmentDoctorPageBinding
import com.decadevs.healthrecords.datastore.UserManager
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorPageFragment : Fragment() {
    private var _binding: FragmentDoctorPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService

    private lateinit var viewModel: HealthRecordsViewModel
    lateinit var userManager: UserManager

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

        observeStaffIdAndImplementApi()

        return binding.root
    }

    private fun observeStaffIdAndImplementApi() {
        userManager.rmUserIdFlow.asLiveData().observe(requireActivity(), { uid ->
            if (uid != "") {
                viewModel.getStaff(uid)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}