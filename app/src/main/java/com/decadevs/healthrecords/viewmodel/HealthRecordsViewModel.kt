package com.decadevs.healthrecords.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.request.ForgotPwdRequest
import com.decadevs.healthrecords.model.request.LoginRequest
import com.decadevs.healthrecords.model.request.ResetPasswordRequest
import com.decadevs.healthrecords.model.response.PatientAllRecordsResponse
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import com.decadevs.healthrecords.model.response.TokenResponse
import com.decadevs.healthrecords.repository.HealthRecordsRepository
import kotlinx.coroutines.launch

class HealthRecordsViewModel(
    private val repository: HealthRecordsRepository,
) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> =
        MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _getStaffResponse: MutableLiveData<Resource<StaffResponse>> =
        MutableLiveData()
    val getStaffResponse: LiveData<Resource<StaffResponse>>
        get() = _getStaffResponse

    private  val _getPatientPatientAllMedicalRecord: MutableLiveData<Resource<PatientAllRecordsResponse>> =
        MutableLiveData()
    val getPatientPatientAllMedicalRecord: LiveData<Resource<PatientAllRecordsResponse>>
    get() = _getPatientPatientAllMedicalRecord

    private val _getTokenResponse: MutableLiveData<Resource<TokenResponse>> =
        MutableLiveData()
    val getTokenResponse: LiveData<Resource<TokenResponse>>
        get() = _getTokenResponse


    private val _getResetPwdResponse: MutableLiveData<Resource<Any>> =
        MutableLiveData()
    val getResetPwdResponse: LiveData<Resource<Any>>
        get() = _getResetPwdResponse

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
}