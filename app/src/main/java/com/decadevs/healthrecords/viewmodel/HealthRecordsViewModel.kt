package com.decadevs.healthrecords.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.request.ForgotPwdRequest
import com.decadevs.healthrecords.model.request.LoginRequest
import com.decadevs.healthrecords.model.request.NurseCommentRequest
import com.decadevs.healthrecords.model.request.RecordUpdateRequest
import com.decadevs.healthrecords.model.request.ResetPasswordRequest
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import com.decadevs.healthrecords.model.response.TokenResponse
import com.decadevs.healthrecords.model.response.*
import com.decadevs.healthrecords.repository.HealthRecordsRepository
import com.decadevs.utils.SessionManager
import com.decadevs.utils.TOKEN
import kotlinx.coroutines.launch

class HealthRecordsViewModel(
    private val repository: HealthRecordsRepository,
    private val context : Context
) : ViewModel() {

    val token = "Bearer ${SessionManager.load(context, TOKEN)}"

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> =
        MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _getStaffResponse: MutableLiveData<Resource<StaffResponse>> =
        MutableLiveData()
    val getStaffResponse: LiveData<Resource<StaffResponse>>
        get() = _getStaffResponse

    private val _getAllPatientMedicalRecord: MutableLiveData<Resource<PatientAllRecordsResponse>> =
        MutableLiveData()
    val getAllPatientMedicalRecord: LiveData<Resource<PatientAllRecordsResponse>>
        get() = _getAllPatientMedicalRecord

    private val _getPatientData: MutableLiveData<Resource<PatientResponse>> = MutableLiveData()
    val getPatientData: LiveData<Resource<PatientResponse>>
        get() = _getPatientData

    private val _getTokenResponse: MutableLiveData<Resource<TokenResponse>> =
        MutableLiveData()
    val getTokenResponse: LiveData<Resource<TokenResponse>>
        get() = _getTokenResponse


    private val _getResetPwdResponse: MutableLiveData<Resource<Any>> =
        MutableLiveData()
    val getResetPwdResponse: LiveData<Resource<Any>>
        get() = _getResetPwdResponse

    private val _medicalRecordResponse: MutableLiveData<Resource<GenericResponseClass<FormData>>> = MutableLiveData()
    val medicalRecordResponse: LiveData<Resource<GenericResponseClass<FormData>>> get() = _medicalRecordResponse

    private val _nurseCommentResponse: MutableLiveData<Resource<GenericResponseClass<NurseCommentRequest>>> = MutableLiveData()
    val nurseCommentResponse: LiveData<Resource<GenericResponseClass<NurseCommentRequest>>> get() = _nurseCommentResponse

    private val _updateMedicalRecord: MutableLiveData<Resource<Any>> = MutableLiveData()
    val updateMedicalRecord: LiveData<Resource<Any>> get() = _updateMedicalRecord

    /** launch coroutine in viewModel scope for login */
    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        _loginResponse.value = repository.login(loginRequest)
    }

    /** launch coroutine in viewModel scope for get staff */
    fun getStaff(uuid: String) = viewModelScope.launch {
        _getStaffResponse.value = repository.getStaff(uuid)
    }

    /** launch coroutine in viewModel scope for forgot pwd */
    fun getTokenResponseForForgotPwd(forgotPwdRequest: ForgotPwdRequest) = viewModelScope.launch {
        _getTokenResponse.value = repository.forgotPwd(forgotPwdRequest)
    }

    fun getResetPwdResponse(resetPasswordRequest: ResetPasswordRequest) = viewModelScope.launch {
        _getResetPwdResponse.value = repository.resetPassword(resetPasswordRequest)
    }

    /** ADD MEDICAL RECORD IN VIEW-MODEL SCOPE OF COROUTINE */
    fun addMedicalRecord(
        activity: Activity, diagnosis: String, prescription: String,
        isSensitive: Boolean, doctorNotes: String,
        patientRegistrationNumber: String, documentFormFiles: String,
        documentDescription: String) = viewModelScope.launch {
        _medicalRecordResponse.value = repository.addMedicalRecord(token, activity, diagnosis, prescription, isSensitive,
                                                doctorNotes, patientRegistrationNumber, documentFormFiles, documentDescription)
    }
    fun getPatientAllRecords(patientId: String) = viewModelScope.launch {
        _getAllPatientMedicalRecord.value = repository.getPatientAllRecords(patientId)
    }

    fun getPatientData(patientId: String) = viewModelScope.launch {
        _getPatientData.value = repository.getPatientData(patientId)
    }

    /** ADD NURSE COMMENT IN  VIEW-MODEL SCOPE OF COROUTINE*/
    fun addNurseComment(nurseCommentRequest: NurseCommentRequest) = viewModelScope.launch {
        _nurseCommentResponse.value = repository.addNurseComment(token, nurseCommentRequest)
    }

    fun updateMedicalRecord(patientRegistrationNumber: String, medicalRecordUpdateRequest: RecordUpdateRequest) = viewModelScope.launch {
        _updateMedicalRecord.value = repository.updatePatientRecord(token, patientRegistrationNumber, medicalRecordUpdateRequest)
    }
}