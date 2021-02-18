package com.decadevs.healthrecords.model.request

data class NurseComment (
    val nurseId: String,
    val patientId: String,
    val note: String
)