package com.example.virtualfridge.data.api.models

data class ApiException(
    val statusCode: Int,
    val message: String?
)