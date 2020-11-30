package com.example.virtualfridge.data.api.models

data class NoteResponse(
    val id: String,
    val text: String,
    val authorFirstName: String,
    val authorLastName: String
)