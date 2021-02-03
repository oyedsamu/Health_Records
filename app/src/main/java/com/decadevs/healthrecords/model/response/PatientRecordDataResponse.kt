package com.decadevs.healthrecords.model.response

data class PatientRecordDataResponse (
    val diagnosis: String,
    val prescription: String,
    val isSensitive: Boolean,
    val doctorNotes: String,
    val doctorOnCall: String,
    val createdAt: String
)