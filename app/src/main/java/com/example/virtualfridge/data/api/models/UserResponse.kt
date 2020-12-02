package com.example.virtualfridge.data.api.models

import com.example.virtualfridge.data.internal.models.User

data class UserResponse(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val familyName: String?,
    val accountConfirmed: Boolean
)

fun UserResponse.mapToUser(): User = this.run {
    User(id, email, firstName, lastName, familyName, accountConfirmed)
}