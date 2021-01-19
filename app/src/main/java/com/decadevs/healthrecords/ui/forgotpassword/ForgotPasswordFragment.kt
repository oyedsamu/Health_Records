package com.decadevs.healthrecords.ui.resetpassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentForgotPasswordBinding
import com.decadevs.healthrecords.model.request.ForgotPwdRequest
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService

    private lateinit var viewModel: HealthRecordsViewModel
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

        val repository = HealthRecordsRepositoryImpl(apiService)
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)

        binding.fragmentForgotPasswordSendBtn.setOnClickListener {
            validateInputField()

            viewModel.getTokenResponse.observe(viewLifecycleOwner, {
                Log.i("Token Response ", "$it")

                when (it) {
                    is Resource.Success -> {
                        val successResponse = it.value.token
                        Log.i("Forgot pwd Response", "$successResponse")
                        //findNavController().navigate(R.id.resetPasswordFragment)
                    }
                    is Resource.Failure -> {
                        Log.i("Forgot Pwd Failure", "${it.errorCode}, $it")
                    }
                }

            })
        }

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
                val forgotPwdRequest = ForgotPwdRequest(email.text.toString(), uID.text.toString())
                viewModel.getTokenResponseForForgotPwd(forgotPwdRequest)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}