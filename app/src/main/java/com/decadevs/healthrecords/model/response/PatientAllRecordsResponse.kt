package com.decadevs.healthrecords.model.response

data class PatientAllRecordsResponse (
    val count: Int,
    val data: ArrayList<PatientRecordDataResponse>
)