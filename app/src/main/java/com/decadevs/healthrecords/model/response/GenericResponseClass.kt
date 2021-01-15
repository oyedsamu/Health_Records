package com.decadevs.healthrecords.model.response

data class GenericResponseClass<T>(
    val title: String?,
    val errors: ErrorResponse?,
    val data: T?
)