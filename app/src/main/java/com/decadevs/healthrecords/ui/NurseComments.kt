package com.decadevs.healthrecords.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentNurseCommentsBinding
import com.decadevs.healthrecords.databinding.FragmentPatientDetailsBinding


class NurseComments : Fragment() {
    private var _binding: FragmentNurseCommentsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding = FragmentNurseCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentNurseCommentBtn.setOnClickListener{
            val nurseComment = binding.fragmentNurseCommentTextInputEt.text
            // Do something with this text.


        }
    }

}