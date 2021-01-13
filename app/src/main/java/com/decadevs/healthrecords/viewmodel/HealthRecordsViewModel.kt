package com.decadevs.healthrecords.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.GenericResponseClass
import com.decadevs.healthrecords.model.LoginRequest
import com.decadevs.healthrecords.model.LoginResponse
import com.decadevs.healthrecords.repository.HealthRecordsRepository
import kotlinx.coroutines.launch

class HealthRecordsViewModel(
    private val repository: HealthRecordsRepository,
) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<GenericResponseClass<LoginResponse>>> =
        MutableLiveData()
    val loginResponse: LiveData<Resource<GenericResponseClass<LoginResponse>>>
        get() = _loginResponse


    /** launch coroutine in viewModel scope for login */
    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        _loginResponse.value = repository.login(loginRequest)
    }
}