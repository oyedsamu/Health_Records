package com.decadevs.healthrecords


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val uID = binding.uniqueIdEditText
        val pwd = binding.passwordEditText


        binding.signInButton.setOnClickListener {
            validateLoginInput(uID, pwd)
        }

        return binding.root
    }

    private fun validateLoginInput(
        uID: TextInputEditText,
        pwd: TextInputEditText
    ) {
        when {
            uID.text?.isEmpty()!! -> {
                uID.error = "Please enter unique ID"
            }
            pwd.text?.isEmpty()!! -> {
                pwd.error = "Please enter your password"
            }
            else -> {
                findNavController().navigate(R.id.doctorPageFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
