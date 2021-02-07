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
import androidx.lifecycle.Observer
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
import com.decadevs.utils.FormValidator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService
    @Inject private lateinit var validator: FormValidator

    private lateinit var viewModel: HealthRecordsViewModel
    private lateinit var email: EditText
    private lateinit var uID: EditText

    private var mProgressDialog: ProgressDialog? = null

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
        val factory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)

        binding.fragmentForgotPasswordSendBtn.setOnClickListener {
            if(validateInputField()) {

                validateInputFieldmProgressDialog =
                    ProgressDialog.show(
                        requireActivity(),
                        "Verifying data",
                        "Please wait...",
                        false,
                        false
                    )

//                binding.forgotPasswordPrgressBarPb.visibility = View.VISIBLE

                val forgotPwdRequest = ForgotPwdRequest(email.text.toString(), uID.text.toString())
                viewModel.getTokenResponseForForgotPwd(forgotPwdRequest)
                email.error = null
                uID.error = null

                viewModel.getTokenResponse.observe(viewLifecycleOwner, Observer{
                    mProgressDialog!!.dismiss()
                    Log.i("Token Response", "$it")
                    when (it) {
                        is Resource.Success -> {
//                            binding.forgotPasswordPrgressBarPb.visibility = View.GONE
                            val successResponse = it.value.token
                            val action =
                                ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToResetPasswordFragment(
                                    successResponse, email.text.toString(), uID.text.toString()
                                )
                            findNavController().navigate(action)
                        }
                        is Resource.Failure -> {
//                            binding.forgotPasswordPrgressBarPb.visibility = View.GONE
                            if (it.errorCode == 404) {
                                Toast.makeText(
                                    requireActivity(),
                                    "Email not found, please input correct email",
                                    Toast.LENGTH_LONG
                                ).show()
                                email.error = "Email not found, please input correct email"
                            } else if (it.errorCode == 500) {
                                email.error = null
                                Toast.makeText(
                                    requireActivity(),
                                    "Something went wrong, please recheck your unique id",
                                    Toast.LENGTH_LONG
                                ).show()
                                uID.error = "Something went wrong, please recheck your unique id"
                            }
                        }
                    }

                })
            }
        }

        return binding.root
    }

    private fun validateInputField(): Boolean {
        return when {
            validator.validateEmail(email.text.toString().trim()) -> {
                email.error = "Please enter your correct email"
                false
            }
            uID.text.toString().trim().isEmpty() -> {
                uID.error = "Please enter your correct unique ID"
                false
            }
            else -> {
                true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}