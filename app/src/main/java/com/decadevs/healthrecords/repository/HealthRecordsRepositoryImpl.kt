package com.decadevs.healthrecords.repository

import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.request.ForgotPwdRequest
import com.decadevs.healthrecords.model.request.LoginRequest
import com.decadevs.healthrecords.model.request.ResetPasswordRequest
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import com.decadevs.healthrecords.model.response.TokenResponse
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

    override suspend fun getStaff(id: String): Resource<StaffResponse> =
        safeApiCall {
            apiService.getStaff(id)
        }

    override suspend fun forgotPwd(pwdRequest: ForgotPwdRequest): Resource<TokenResponse> =
        safeApiCall {
            apiService.forgotPassword(pwdRequest)
        }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Resource<Any> =
        safeApiCall {
        apiService.resetPassword(resetPasswordRequest)
    }


}