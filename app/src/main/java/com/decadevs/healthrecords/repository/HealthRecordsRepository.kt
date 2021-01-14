package com.decadevs.healthrecords.repository

import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.LoginRequest
import com.decadevs.healthrecords.model.response.GenericResponseClass
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.StaffResponse

interface HealthRecordsRepository {
    suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse>

    suspend fun getStaff(id: String): Resource<GenericResponseClass<StaffResponse>>
}