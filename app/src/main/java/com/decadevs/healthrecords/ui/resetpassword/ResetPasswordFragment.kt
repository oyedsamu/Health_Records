package com.decadevs.healthrecords.ui.resetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!


    private val args: ResetPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_reset_password, container, false)
        _binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)

        // testing
        binding.fragmentResetPasswordSendBtn.setOnClickListener { findNavController().navigate(R.id.forgotPasswordFragment) }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}