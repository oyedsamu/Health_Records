package com.decadevs.healthrecords.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.request.LoginRequest
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.StaffResponse
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


    /** launch coroutine in viewModel scope for login */
    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        _loginResponse.value = repository.login(loginRequest)
    }

    fun getStaff(uuid:String) = viewModelScope.launch {
        _getStaffResponse.value = repository.getStaff(uuid)
    }
}