package com.decadevs.healthrecords.repository

import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.GenericResponseClass
import com.decadevs.healthrecords.model.LoginRequest
import com.decadevs.healthrecords.model.LoginResponse

interface HealthRecordsRepository {
    suspend fun login(loginRequest: LoginRequest): Resource<GenericResponseClass<LoginResponse>>
}