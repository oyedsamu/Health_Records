package com.decadevs.healthrecords.model.response

data class StaffDataResponse(
    val firstname: String,
    val lastname: String,
    val email: String,
    val phoneNumber: String,
    val uniqueId: String,
    val gender: String,
    val createdAt: String,
    val healthcareProviderName: String,
    val roleName: String,
    val state: String,
    val city: String,
    val street: String,
    val roleId: String
)