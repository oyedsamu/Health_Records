package com.decadevs.healthrecords.repository

import com.decadevs.healthrecords.api.LoginAuthApi
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.GenericResponseClass
import com.decadevs.healthrecords.model.LoginRequest
import com.decadevs.healthrecords.model.LoginResponse
import javax.inject.Inject

class HealthRecordsRepositoryImpl
@Inject
constructor(
    private val loginAuthApi: LoginAuthApi
) : BaseRepository(), HealthRecordsRepository {
    override suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> =
        safeApiCall {
            loginAuthApi.login(loginRequest)
        }


}