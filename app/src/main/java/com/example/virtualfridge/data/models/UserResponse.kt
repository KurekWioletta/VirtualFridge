package com.example.virtualfridge.data.models

data class UserResponse(
    val email: String,
    val firstName: String,
    val lastName: String,
    val accountConfirmed: Boolean
)