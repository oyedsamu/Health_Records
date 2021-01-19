package com.decadevs.healthrecords.ui.resetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var password: EditText
    private lateinit var confirmPwd: EditText


    private val args: ResetPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_reset_password, container, false)
        _binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)

        val token = args.token
        val email = args.email
        val uid = args.uid


        binding.fragmentResetPasswordSendBtn.setOnClickListener {
            validateResetInput(token, email, uid)
        }

        return binding.root
    }

    private fun validateResetInput(token: String, email: String, uid: String) {
        when {
            password.text.isEmpty() -> {
                password.error = "Please enter your new password"
            }

            confirmPwd.text.isEmpty() -> {
                confirmPwd.error = "Please re-enter the password above"
            }

            confirmPwd.text != password.text -> {
                confirmPwd.error = "Confirm password does not match password"
            }

            else -> {
                findNavController().navigate(R.id.loginFragment)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}