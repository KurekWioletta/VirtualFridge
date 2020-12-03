package com.example.virtualfridge.utils

import android.util.Patterns
import java.time.LocalDate
import java.util.regex.Pattern

abstract class BaseValidationViewModel() {
    abstract fun toList(): List<String?>
}

fun String.validate(message: String, validator: (String) -> Boolean): String? =
    if (validator(this)) null else message

// true if no error was found
fun BaseValidationViewModel.validationResult(): Boolean =
    this.toList().firstOrNull { !it.isNullOrEmpty() } == null

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidDate(): Boolean {
    return if (this.isNotEmpty() && Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$").matcher(this)
            .matches()
    ) {
        try {
            LocalDate.parse(this, dateTimeFormatter)
            true
        } catch (e: Exception) {
            false
        }
    } else {
        false
    }
}

fun String.isValidPassword(): Boolean = this.isNotEmpty() && this.length >= 6