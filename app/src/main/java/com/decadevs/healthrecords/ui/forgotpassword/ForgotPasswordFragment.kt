package com.decadevs.healthrecords.ui.resetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var email: EditText
    private lateinit var uID: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_forgot_password, container, false)
        _binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)

        email = binding.fragmentForgotPasswordEmailEt
        uID = binding.fragmentForgotPasswordUniqueIdEt

        binding.fragmentForgotPasswordSendBtn.setOnClickListener { validateInputField() }



        return binding.root
    }

    private fun validateInputField() {
        when {
            email.text.isEmpty() -> {
                email.error = "Please enter your correct email"
            }
            uID.text.isEmpty() -> {
                uID.error = "Please enter your correct unique ID"
            }
            else -> {

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}