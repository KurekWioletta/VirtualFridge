package com.example.virtualfridge.data.api.models

data class EventResponse(
    val id: String,
    val title: String,
    val description: String?,
    val place: String?,
    val startDate: String,
    val endDate: String
)