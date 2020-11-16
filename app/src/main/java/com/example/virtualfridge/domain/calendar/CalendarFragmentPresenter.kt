package com.example.virtualfridge.domain.calendar

import android.util.Log
import com.example.virtualfridge.domain.calendar.notes.NoteViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalendarFragmentPresenter @Inject constructor(
    private val view: CalendarFragment
) {

    private val monthYearFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectedDayFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")

    fun init() {
        // TODO: download notes, cache notes, update view
        view.updateNotes(
            view.currentDay, mapOf(
                view.currentDay.plusDays(1) to true,
                view.currentDay.plusDays(2) to true
            )
        )
    }

    fun monthChanged(yearMonth: YearMonth) = view.updateMonth(monthYearFormatter.format(yearMonth))

    fun dateSelected(date: LocalDate) {
        if (view.selectedDate != date) {
            view.updateDate(
                date, selectedDayFormatter.format(date), listOf(
                    NoteViewModel(
                        "",
                        "titleNote2",
                        "contentNote2",
                        "placeNote2",
                        view.currentDay,
                        view.currentDay
                    ),
                    NoteViewModel(
                        "",
                        "titleNote1",
                        "contentNote1",
                        "placeNote1",
                        view.currentDay,
                        view.currentDay
                    )
                )
            )
        }
    }

    fun noteClicked(note: NoteViewModel) {
        Log.i("TMP", note.title)
    }
}