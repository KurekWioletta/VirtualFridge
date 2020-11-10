package com.example.virtualfridge.domain.calendar

import android.util.Log
import com.example.virtualfridge.domain.calendar.notes.NoteViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalendarPresenter @Inject constructor(
    private val view: CalendarFragment
) {

    private val monthYearFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectedDayFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")

    fun dateSelected(date: LocalDate) {
        if (view.selectedDate != date) {
            view.updateCalendar(date, selectedDayFormatter.format(date))
        }
    }

    fun noteClicked(note: NoteViewModel) {
        Log.i("TMP", note.title)
    }
}