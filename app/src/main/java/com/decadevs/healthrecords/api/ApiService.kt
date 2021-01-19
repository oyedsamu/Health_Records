package com.decadevs.healthrecords.api

import com.decadevs.healthrecords.model.request.ForgotPwdRequest
import com.decadevs.healthrecords.model.request.LoginRequest
import com.decadevs.healthrecords.model.request.ResetPasswordRequest
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import com.decadevs.healthrecords.model.response.TokenResponse
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

}