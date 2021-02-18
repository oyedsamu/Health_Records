package com.decadevs.healthrecords.model.request

data class RecordUpdateRequest (
    val medicalRecordId: String,
    val diagnosis: String,
    val prescription: String,
    val isSensitive: Boolean,
    val doctorNotes: String
)