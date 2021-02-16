package com.decadevs.healthrecords.model.response

data class GenericResponseClass<T>(
    val message: String?,
    val success: Boolean?,
    val data: T?
)