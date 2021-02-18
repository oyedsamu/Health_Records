package com.decadevs.healthrecords.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decadevs.healthrecords.R
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.databinding.FragmentNurseCommentsBinding
import com.decadevs.healthrecords.databinding.FragmentPatientDetailsBinding
import com.decadevs.healthrecords.model.request.NurseCommentRequest
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl
import com.decadevs.healthrecords.viewmodel.HealthRecordsViewModel
import com.decadevs.healthrecords.viewmodel.ViewModelFactory
import com.decadevs.utils.hideKeyboard
import com.decadevs.utils.showAlertDialog
import com.decadevs.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NurseComments : Fragment() {
    private var _binding: FragmentNurseCommentsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var apiService: ApiService
    private lateinit var viewModel: HealthRecordsViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var repository: HealthRecordsRepositoryImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding = FragmentNurseCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** SET UP VIEW-MODEL */
        repository = HealthRecordsRepositoryImpl(apiService)
        viewModelFactory = ViewModelFactory(repository, requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory).get(HealthRecordsViewModel::class.java)

        /** VALIDATE FORM AND MAKE NETWORK CALL ON BUTTON CLICK */
        binding.fragmentNurseCommentBtn.setOnClickListener{
            view.hideKeyboard()

            val nurseComment = binding.fragmentNurseCommentTextInputEt.text.toString()
            if(nurseComment.isNotEmpty()) {
                binding.nurseCommentProgressBarPb.visibility = View.VISIBLE
                addNurseComment(nurseComment)
            } else {
                showToast("Please provide a comment to continue.", requireContext())
            }
        }

        /** BACK BUTTON */
        binding.nurseCommentsBackIb.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addNurseComment(comment: String) {
        showToast(comment, requireContext())
//        DYNAMICALLY PASS IN PATIENT AND NURSE ID FROM PREVIOUS SCREEN WHEN CODE IS READY
        val commentRequest = NurseCommentRequest("STA66121-R2", "23657E5", comment)
        viewModel.addNurseComment(commentRequest)

        /** OBSERVE COMMENT RESPONSE */
        viewModel.nurseCommentResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Success -> {
                    Log.i("commentResponse", it.value.message.toString())
                    binding.nurseCommentProgressBarPb.visibility = View.GONE
                    Toast.makeText(this.context, "Successful", Toast.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {
                    Log.i("commentResponse", it.errorBody.toString())
                    binding.nurseCommentProgressBarPb.visibility = View.GONE
                    Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}