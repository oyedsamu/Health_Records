package com.decadevs.healthrecords.repository

import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.request.ForgotPwdRequest
import com.decadevs.healthrecords.model.request.LoginRequest
import com.decadevs.healthrecords.model.request.MedicalRecordRequest
import com.decadevs.healthrecords.model.request.ResetPasswordRequest
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.MedicalRecordResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import com.decadevs.healthrecords.model.response.TokenResponse

interface HealthRecordsRepository {
    suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse>

    suspend fun getStaff(id: String): Resource<StaffResponse>

    suspend fun forgotPwd(pwdRequest: ForgotPwdRequest): Resource<TokenResponse>

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Resource<Any>

    suspend fun addMedicalRecord(medicalRecordRequest: MedicalRecordRequest): Resource<MedicalRecordResponse>
}