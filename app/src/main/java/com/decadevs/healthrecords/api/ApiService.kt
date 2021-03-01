package com.decadevs.healthrecords.api

import com.decadevs.healthrecords.model.request.*
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.MedicalRecordResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import com.decadevs.healthrecords.model.response.TokenResponse
import com.decadevs.healthrecords.model.response.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {


    @POST("Auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse


    @GET("Staff/{id}")
    suspend fun getStaff(
        @Path("id") id: String
    ): StaffResponse

    @POST("Auth/forgotpassword")
    suspend fun forgotPassword(
        @Body forgotPwdRequest: ForgotPwdRequest
    ): TokenResponse


    @POST("Auth/resetPassword")
    suspend fun resetPassword(
        @Body resetPwdRequest: ResetPasswordRequest
    )

    @POST("MedicalRecord/Create")
    suspend fun addMedicalRecord(
        @Header("Authorization") token: String,
        @Body medicalRecordRequest: RequestBody
    ): GenericResponseClass<FormData>

    //MedicalRecordResponse
    @GET("MedicalRecord/GetAllMedicalRecords/{id}/1")
    suspend fun getAllHealthRecords(
        @Path("id") id: String
    ): PatientAllRecordsResponse

    @GET("Patient/{patientRegNum}")
    suspend fun getPatientData(
        @Path("patientRegNum") patientRegNum: String
    ): PatientResponse

    @POST("NurseNote")
    suspend fun addNurseNote(
        @Header("Authorization") token: String,
        @Body nurseCommentRequest: NurseCommentRequest
    ): GenericResponseClass<NurseCommentRequest>

    @PATCH("MedicalRecord/update/{patientRegNum}")
    suspend fun updatePatientRecord(
        @Header("Authorization") token: String,
        @Path("patientRegNum") regNum: String,
        @Body medicalRecordUpdateRequest: RecordUpdateRequest
    )

}