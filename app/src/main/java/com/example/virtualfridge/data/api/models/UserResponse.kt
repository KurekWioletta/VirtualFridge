package com.example.virtualfridge.data.api.models

import com.example.virtualfridge.data.internal.models.User

data class UserResponse(
    val email: String,
    val firstName: String,
    val lastName: String,
    val familyName: String?,
    val accountConfirmed: Boolean
)

fun UserResponse.mapToUser(): User = this.run {
    User(email, firstName, lastName, familyName, accountConfirmed)
}