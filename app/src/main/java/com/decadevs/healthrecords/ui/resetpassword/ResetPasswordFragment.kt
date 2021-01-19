package com.decadevs.healthrecords.ui.resetpassword

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentResetPasswordBinding
import com.decadevs.healthrecords.model.request.ResetPasswordRequest
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService

    private lateinit var viewModel: HealthRecordsViewModel

    private lateinit var password: EditText
    private lateinit var confirmPwd: EditText

    private var mProgressDialog: ProgressDialog? = null
    private val args: ResetPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_reset_password, container, false)
        _binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)

        password = binding.fragmentResetPasswordEt
        confirmPwd = binding.fragmentResetConfirmPasswordEt

        val token = args.token
        val email = args.email
        val uid = args.uid

        val repository = HealthRecordsRepositoryImpl(apiService)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)


        binding.fragmentResetPasswordSendBtn.setOnClickListener {
            validateResetInput(token, email, uid)

            viewModel.getResetPwdResponse.observe(viewLifecycleOwner, {
                mProgressDialog!!.dismiss()
                when (it) {
                    is Resource.Success -> {
                        findNavController().navigate(R.id.loginFragment)
                    }
                    is Resource.Failure -> {
                        Log.i("Reset failed", "${it.errorCode}, $it")
                    }
                }
            })
        }

        return binding.root
    }

    private fun validateResetInput(token: String, email: String, uid: String) {
        when {
            password.text.isEmpty() -> {
                password.error = "Please enter your new password"
            }

            confirmPwd.text.isEmpty() -> {
                confirmPwd.error = "Please confirm password"
            }

            confirmPwd.text.toString() != password.text.toString() -> {
                confirmPwd.error = "Confirm password does not match password"
            }

            else -> {
                mProgressDialog =
                    ProgressDialog.show(
                        requireActivity(),
                        "Resetting password",
                        "Please wait...",
                        false,
                        false
                    )

                val resetPwdRequest = ResetPasswordRequest(
                    email,
                    uid,
                    password.text.toString(),
                    confirmPwd.text.toString(),
                    token
                )
                viewModel.getResetPwdResponse(resetPwdRequest)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}