package com.decadevs.healthrecords.repository

import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.LoginRequest
import com.decadevs.healthrecords.model.response.GenericResponseClass
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import javax.inject.Inject

class HealthRecordsRepositoryImpl
@Inject
constructor(
    private val apiService: ApiService
) : BaseRepository(), HealthRecordsRepository {
    override suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> =
        safeApiCall {
            apiService.login(loginRequest)
        }

    override suspend fun getStaff(id: String): Resource<GenericResponseClass<StaffResponse>> =
        safeApiCall {
            apiService.getStaff(id)
        }



}