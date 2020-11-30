package com.example.virtualfridge.data.api.models

data class FamilyMemberResponse(
    val id: String,
    val firstName: String,
    val lastName: String
)

data class InvitationResponse(
    val id: String,
    val familyName: String
)