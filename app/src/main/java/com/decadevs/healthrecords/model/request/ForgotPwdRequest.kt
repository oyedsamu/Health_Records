package com.decadevs.healthrecords.model.request

data class ForgotPwdRequest (
    val email: String,
    val uniqueId: String
)