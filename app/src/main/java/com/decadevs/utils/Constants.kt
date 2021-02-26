package com.decadevs.utils

import com.decadevs.healthrecords.model.response.PatientDataResponse

const val SHARED_PREFS: String = "sharedPrefs"
const val TOKEN : String = "token"
const val LOG_OUT = "log_out"

/** CURRENT STAFF DETAILS */
var currentStaffId = ""
var roleName = ""

/** CURRENT PATIENT DETAILS */
var currentPatientId = ""
var patientBloodGroup = ""
var patientGenotype = ""
var patientAllergies = ""
var patientDisabilities = ""
lateinit var patientData: PatientDataResponse
