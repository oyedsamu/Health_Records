package com.decadevs.healthrecords.model.response

data class PatientResponse (
    val data: PatientDataResponse,
    val success: Boolean
)