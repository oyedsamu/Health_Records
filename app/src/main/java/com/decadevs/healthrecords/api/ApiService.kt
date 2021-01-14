package com.decadevs.healthrecords.api

import com.decadevs.healthrecords.model.response.GenericResponseClass
import com.decadevs.healthrecords.model.LoginRequest
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import retrofit2.http.*

interface ApiService {


    @POST("Auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse


    @GET("Staff/{id}")
    suspend fun getStaff(
        @Path("id") id: String
    ): GenericResponseClass<StaffResponse>

}