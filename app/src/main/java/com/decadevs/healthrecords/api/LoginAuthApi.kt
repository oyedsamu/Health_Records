package com.decadevs.healthrecords.api

import com.decadevs.healthrecords.model.GenericResponseClass
import com.decadevs.healthrecords.model.LoginRequest
import com.decadevs.healthrecords.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginAuthApi {


    @POST("Auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

}