package com.decadevs.healthrecords.model.request

data class MedicalRecordRequest(
    val diagnosis: String,
    val prescription: String,
    val isSensitive: Boolean,
    val doctorNotes: String,
    val patientRegistrationNumber: String
)
