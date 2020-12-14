package com.decadevs.healthrecords.ui.doctorpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentDoctorPageBinding
import com.decadevs.healthrecords.ui.patientdetails.PatientDetailsFragment

class DoctorPageFragment : Fragment() {

    private var _binding: FragmentDoctorPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDoctorPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.profileImage.setOnClickListener {
//            fragmentManager?.beginTransaction()?.apply {
//                replace(R.id.framelayout, PatientDetailsFragment())
//                    .addToBackStack(null)
//                    .commit()
//            }
//        }
    }
}