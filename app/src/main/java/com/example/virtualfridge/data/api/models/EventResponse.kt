package com.example.virtualfridge.data.api.models

import java.time.LocalDate

data class EventResponse(
    val id: String,
    val title: String,
    val description: String?,
    val place: String?,
    val startDate: LocalDate,
    val endDate: LocalDate
)