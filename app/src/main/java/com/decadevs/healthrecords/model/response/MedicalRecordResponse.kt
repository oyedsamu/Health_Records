package com.decadevs.healthrecords.model.response

data class MedicalRecordResponse(
    val data: FormData,
    val success: Boolean,
    val message: String
)

data class FormData(
    val diagnosis: String,
    val prescription: String,
    val isSensitive: Boolean,
    val doctorNotes: String,
    val patientRegistrationNumber: String,
    val documentFormFiles: List<Int>?,
    val documentDescription: String
)
