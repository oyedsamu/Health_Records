package com.decadevs.healthrecords.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import com.decadevs.utils.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
    lateinit var rememberMe: CheckBox
    lateinit var userManager: UserManager
    private var savedToken: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)



        val uniqueID = binding.fragmentLoginUniqueIdEt
        val doctorPassword = binding.fragmentLoginPasswordEt
        val progressBar = binding.progressBarLayout
        val uniqueIdLayout = binding.fragmentLoginUniqueIdTl
        val doctorPasswordLayout = binding.fragmentLoginPasswordTl

        /** SKIP LOGIN IF USER HAS LOGGED IN BEFORE */
        savedToken = SessionManager.load(requireContext(), TOKEN).trim()
        if(savedToken != "") findNavController().navigate(R.id.action_loginFragment_to_doctorPageActivity)

        val uID = binding.fragmentLoginUniqueIdEt
        val pwd = binding.fragmentLoginPasswordEt

        rememberMe = binding.rememberMe




        val repository = HealthRecordsRepositoryImpl(apiService)
        val factory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, factory).get(HealthRecordsViewModel::class.java)
        userManager = UserManager(requireActivity())

        binding.forgetPwdButton.setOnClickListener { findNavController().navigate(R.id.forgotPasswordFragment) }


        /**
         * Set uid and password into login edit fields if rememberMe already saved
         */
        checkAndImplementRememberMe(doctorPassword, uniqueID)

        binding.signInButton.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            if(validateLoginInput(uniqueID, doctorPassword, uniqueIdLayout, doctorPasswordLayout)) {

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

                progressBar.visibility = View.VISIBLE

                viewModel.loginResponse.observe(viewLifecycleOwner, Observer{
                   // Log.i("Login Response ", "$it")

                    when (it) {
                        is Resource.Success -> {
                            val successResponse = it.value.message

                           // Toast.makeText(this.context, it.value.data, Toast.LENGTH_LONG).show()
                            Log.i("Login Response", "$successResponse")
                            progressBar.visibility = View.INVISIBLE

                            Log.i("Login Response", "$successResponse")

                            Toast.makeText(this.context, "Login Successful", Toast.LENGTH_LONG).show()
                            progressBar.visibility = View.GONE


                            // on login, save token to sharedPref and go doctorPageActivity
                            SessionManager.save(requireContext(), TOKEN, it.value.data)
                            findNavController().navigate(R.id.action_loginFragment_to_doctorPageActivity)
                            //  findNavController().navigate(R.id.doctorPageFragment)
                        }
                        is Resource.Failure -> {
                            Log.i("Login Response Failure", "${it.errorBody}, ${it.isNetworkError}")

                            showToast("Invalid login details", requireActivity())
                            progressBar.visibility = View.INVISIBLE


                            Toast.makeText(requireContext(), "Login not successful, Please make sure you input your correct details", Toast.LENGTH_LONG).show()
                            progressBar.visibility = View.GONE

                        }
                    }
                })
            }

        }

        return binding.root
    }

    private fun validateLoginInput(
        uID: TextInputEditText,
        pwd: TextInputEditText,
        uniqueIDLayout : TextInputLayout,
        doctorPassword : TextInputLayout

    ): Boolean {
        val progressBar = binding.progressBarLayout
        when {
            uID.text?.trim()?.isEmpty()!! -> {

                uniqueIDLayout.error = "Please enter unique ID"
                progressBar.visibility = View.INVISIBLE
                return false
            }
            pwd.text?.trim()?.isEmpty()!! -> {
                doctorPassword.error = "Please enter your password"
                progressBar.visibility = View.INVISIBLE
                return false
            }

            else -> {
                return true
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


    override fun onDestroyView() {
        super.onDestroyView()
     // viewModel.loginResponse.removeObservers(viewLifecycleOwner)
        _binding = null
    }

}
