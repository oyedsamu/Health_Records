package com.decadevs.healthrecords.model.response

data class PatientDataResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val registrationNumber: String,
    val weight: Float,
    val height: Int,
    val gender: String,
    val doB: String,
    val state: String,
    val city: String,
    val street: String,
    val bloodGroup: String,
    val genoType: String,
    val allergies: String,
    val disability: String,
    val createdAt: String,
    val updatedAt: String
)