package com.example.virtualfridge.data.internal.models

data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val accountConfirmed: Boolean
)