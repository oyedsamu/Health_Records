package com.decadevs.healthrecords.repository

import android.app.Activity
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.request.*
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.MedicalRecordResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import com.decadevs.healthrecords.model.response.TokenResponse
import com.decadevs.healthrecords.model.response.*

interface HealthRecordsRepository {
    suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse>

    suspend fun getStaff(id: String): Resource<StaffResponse>

    suspend fun forgotPwd(pwdRequest: ForgotPwdRequest): Resource<TokenResponse>

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Resource<Any>

    suspend fun addMedicalRecord(
        token: String, activity: Activity, diagnosis: String,
        prescription: String, isSensitive: Boolean, doctorNotes: String,
        patientRegistrationNumber: String, documentFormFiles: String,
        documentDescription: String
    ): Resource<GenericResponseClass<FormData>>

    suspend fun getPatientAllRecords(patientId: String): Resource<PatientAllRecordsResponse>

    suspend fun getPatientData(patientId: String): Resource<PatientResponse>

    suspend fun addNurseComment(token: String, nurseCommentRequest: NurseCommentRequest): Resource<GenericResponseClass<NurseCommentRequest>>

    suspend fun updatePatientRecord(
        token: String,
        patientRegistrationNumber: String,
        medicalRecordUpdateRequest: RecordUpdateRequest
    ): Resource<Any>
}