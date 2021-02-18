package com.decadevs.healthrecords.model.request

data class NurseCommentRequest (
    val nurseId: String,
    val patientId: String,
    val note: String
)