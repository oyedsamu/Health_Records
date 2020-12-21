package com.decadevs.healthrecords.ui.prescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentDoctorPrescriptionBinding


class DoctorPrescriptionFragment : Fragment() {

    private var _binding : FragmentDoctorPrescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoctorPrescriptionBinding.inflate(inflater, container, false)

        val items = listOf<String>("Group A", "Group B", "Group AB", "Group O")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_dropdown, items)

        return binding.root
    }

}