package com.decadevs.healthrecords.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentLoginBinding
import com.decadevs.healthrecords.datastore.UserManager
import com.decadevs.healthrecords.model.request.LoginRequest
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import com.decadevs.utils.SessionManager
import com.decadevs.utils.TOKEN
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apiService: ApiService

    private lateinit var viewModel: HealthRecordsViewModel
    lateinit var progressBar: ProgressBar
    lateinit var rememberMe: CheckBox
    lateinit var userManager: UserManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val uID = binding.uniqueIdEditText
        val pwd = binding.passwordEditText
        rememberMe = binding.rememberMe

        progressBar = binding.progressBarLayout.fragmentMainProgressBar

        val repository = HealthRecordsRepositoryImpl(apiService)
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)
        userManager = UserManager(requireActivity())

        binding.forgetPwdButton.setOnClickListener { findNavController().navigate(R.id.forgotPasswordFragment) }


        /**
         * Set uid and password into login edit fields if rememberMe already saved
         */
        checkAndImplementRememberMe(pwd, uID)

        binding.signInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            validateLoginInput(uID, pwd)

            viewModel.loginResponse.observe(viewLifecycleOwner, {
                Log.i("Login Response ", "$it")

                when (it) {
                    is Resource.Success -> {
                        val successResponse = it.value.message
                        Log.i("Login Response", "$successResponse")
                        progressBar.visibility = View.GONE

                        // on login, save token to sharedPref and go doctorPageActivity
                        SessionManager.save(requireContext(), TOKEN, successResponse)
                        findNavController().navigate(R.id.action_loginFragment_to_doctorPageActivity)
                      //  findNavController().navigate(R.id.doctorPageFragment)
                    }
                    is Resource.Failure -> {
                        Log.i("Login Response Failure", "${it.errorBody}, ${it.isNetworkError}")
                        progressBar.visibility = View.GONE
                    }
                }

            })
        }

        return binding.root
    }

    private fun validateLoginInput(
        uID: TextInputEditText,
        pwd: TextInputEditText
    ) {
        when {
            uID.text?.trim()?.isEmpty()!! -> {
                uID.error = "Please enter unique ID"
            }
            pwd.text?.trim()?.isEmpty()!! -> {
                pwd.error = "Please enter your password"
            }
            else -> {
                val loginRequest = LoginRequest(uID.text!!.trim().toString(), pwd.text!!.trim()?.toString())
                viewModel.login(loginRequest)

                if (rememberMe.isChecked) {
                    //Save user login data to DataStore
                    GlobalScope.launch {
                        userManager.createRememberMeSession(
                            uID.text!!.trim()?.toString(),
                            pwd.text!!.trim()?.toString()
                        )
                    }
                }
            }
        }
    }


    private fun checkAndImplementRememberMe(password: EditText, uID: EditText) {
        userManager.rmUserIdFlow.asLiveData().observe(requireActivity(), { uid ->
            if (uid != "") {
                uID.setText(uid)
                rememberMe.isChecked = true
            }
        })

        userManager.rmUserPasswordFlow.asLiveData().observe(requireActivity(), { pwd ->
            if (pwd != "") {
                password.setText(pwd)
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        viewModel.loginResponse.removeObservers(viewLifecycleOwner)
    }

}
