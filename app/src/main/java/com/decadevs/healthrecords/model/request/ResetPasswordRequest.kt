package com.decadevs.healthrecords.model.request

data class ResetPasswordRequest(
    val email: String,
    val uniqueId: String,
    val password: String,
    val confirmPassword: String,
    val token: String
)