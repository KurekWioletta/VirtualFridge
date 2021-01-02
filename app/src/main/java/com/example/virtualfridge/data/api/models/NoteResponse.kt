package com.example.virtualfridge.data.api.models

data class NoteResponse(
    val id: String,
    val text: String,
    val addressFirstName: String?,
    val addressLastName: String?,
    val authorFirstName: String?,
    val authorLastName: String?
)