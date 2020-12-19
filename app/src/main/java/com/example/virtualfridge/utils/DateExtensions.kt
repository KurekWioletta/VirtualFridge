package com.example.virtualfridge.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

// iterator stworzony po to zeby moc iterowac po datach (przydaje sie w kalendarzu)
// https://www.netguru.com/codestories/traversing-through-dates-with-kotlin-range-expressions
class DateIterator(
    val startDate: LocalDate,
    private val endDateInclusive: LocalDate,
    val stepDays: Long
) : Iterator<LocalDate> {
    private var currentDate = startDate

    override fun hasNext() = currentDate <= endDateInclusive

    override fun next(): LocalDate {
        val next = currentDate
        currentDate = currentDate.plusDays(stepDays)
        return next
    }
}

class DateProgression(
    override val start: LocalDate,
    override val endInclusive: LocalDate,
    val stepDays: Long = 1
) : Iterable<LocalDate>, ClosedRange<LocalDate> {

    override fun iterator(): Iterator<LocalDate> =
        DateIterator(start, endInclusive, stepDays)
}

operator fun LocalDate.rangeTo(other: LocalDate) = DateProgression(this, other)

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
