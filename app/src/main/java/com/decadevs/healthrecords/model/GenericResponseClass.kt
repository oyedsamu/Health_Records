package com.decadevs.healthrecords.model

data class GenericResponseClass<T>(
    val title: String?,
    val errors: ErrorResponse?,
    val data: T?
)