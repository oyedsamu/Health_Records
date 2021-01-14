package com.decadevs.healthrecords.model.response

data class StaffResponse(
    val data: StaffDataResponse,
    val success: Boolean,
    val message: String
)