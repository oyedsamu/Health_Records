package com.decadevs.healthrecords.repository

import android.app.Activity
import com.decadevs.healthrecords.api.ApiService
import com.decadevs.healthrecords.api.Resource
import com.decadevs.healthrecords.model.request.*
import com.decadevs.healthrecords.model.response.LoginResponse
import com.decadevs.healthrecords.model.response.MedicalRecordResponse
import com.decadevs.healthrecords.model.response.StaffResponse
import com.decadevs.healthrecords.model.response.TokenResponse
import com.decadevs.healthrecords.model.response.*
import okhttp3.MultipartBody
import javax.inject.Inject

class HealthRecordsRepositoryImpl
@Inject
constructor(
    private val apiService: ApiService
) : BaseRepository(), HealthRecordsRepository {
    override suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> =
        safeApiCall {
            apiService.login(loginRequest)
        }

    override suspend fun getStaff(id: String): Resource<StaffResponse> =
        safeApiCall {
            apiService.getStaff(id)
        }

    override suspend fun forgotPwd(pwdRequest: ForgotPwdRequest): Resource<TokenResponse> =
        safeApiCall {
            apiService.forgotPassword(pwdRequest)
        }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Resource<Any> =
        safeApiCall {
            apiService.resetPassword(resetPasswordRequest)
        }

    override suspend fun addMedicalRecord(
        token: String,
        activity: Activity,
        diagnosis: String,
        prescription: String,
        isSensitive: Boolean,
        doctorNotes: String,
        patientRegistrationNumber: String,
        documentFormFiles: String,
        documentDescription: String
    ): Resource<GenericResponseClass<FormData>> = safeApiCall {

        val reqBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("Diagnosis", diagnosis)
            .addFormDataPart("Prescription", prescription)
            .addFormDataPart("IsSensitive", isSensitive.toString())
            .addFormDataPart("DoctorNotes", doctorNotes)
            .addFormDataPart("PatientRegistrationNumber", patientRegistrationNumber)
            .addFormDataPart("DocumentFormFiles", documentFormFiles)
            .addFormDataPart("DocumentDescription", documentDescription)
            .build()

        apiService.addMedicalRecord(token, reqBody)
    }


    override suspend fun getPatientAllRecords(patientId: String): Resource<PatientAllRecordsResponse> =
        safeApiCall {
            apiService.getAllHealthRecords(patientId)
        }

    override suspend fun getPatientData(patientId: String): Resource<PatientResponse> =
        safeApiCall {
            apiService.getPatientData(patientId)
        }

    override suspend fun addNurseComment(
        token: String,
        nurseCommentRequest: NurseCommentRequest
    ): Resource<GenericResponseClass<NurseCommentRequest>> = safeApiCall {
        apiService.addNurseNote(token, nurseCommentRequest)
    }

    override suspend fun updatePatientRecord(
        token: String,
        patientRegistrationNumber: String,
        medicalRecordUpdateRequest: RecordUpdateRequest
    ): Resource<Any> = safeApiCall {
        apiService.updatePatientRecord(token, patientRegistrationNumber, medicalRecordUpdateRequest)
    }
}