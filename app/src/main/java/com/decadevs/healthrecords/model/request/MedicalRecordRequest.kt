package com.decadevs.healthrecords.model.request

data class MedicalRecordRequest(
    val Diagnosis: String,
    val Prescription: String,
    val IsSensitive: Boolean?,
    val DoctorNotes: String,
    val PatientRegistrationNumber: String,
    val DocumentFormFiles: List<String>?,
    val DocumentDescription: String?
)
