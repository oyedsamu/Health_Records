package com.decadevs.healthrecords.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MedicalRecordResponse(
    val data: FormData,
    val success: Boolean,
    val message: String
)

@Parcelize
data class FormData(
    val diagnosis: String,
    val prescription: String,
    val isSensitive: Boolean?,
    val doctorNotes: String,
    val patientRegistrationNumber: String,
    val documentFormFiles: ArrayList<Int>?,
    val documentDescription: String
) : ParentResponse, Parcelable
