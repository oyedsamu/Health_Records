package com.decadevs.healthrecords.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.LoginAuthApi
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentLoginBinding
import com.decadevs.healthrecords.model.LoginRequest
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var loginAuthApi: LoginAuthApi

    private lateinit var viewModel: HealthRecordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val uID = binding.uniqueIdEditText
        val pwd = binding.passwordEditText


        val repository = HealthRecordsRepositoryImpl(loginAuthApi)
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)

        viewModel.loginResponse.observe(viewLifecycleOwner, {
            Log.i("Login Response ", "$it")

            when (it) {
                is Resource.Success -> {
                    val successResponse = it.value.message
                    Log.i("Login Response", "$successResponse")
                    findNavController().navigate(R.id.doctorPageFragment)
                }
                is Resource.Failure -> {
                    Log.i("Login Response Failure", "${it.errorBody}, ${it.isNetworkError}")
                }
            }

        })


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
                val loginRequest = LoginRequest(uID.text.toString(), pwd.text.toString())
                viewModel.login(loginRequest)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
