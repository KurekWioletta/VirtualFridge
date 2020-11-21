package com.example.virtualfridge.domain.calendar

import android.util.Log
import com.example.virtualfridge.domain.calendar.CalendarFragment.FamilyMember
import com.example.virtualfridge.domain.calendar.events.EventViewModel
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
        // TODO: download family members, download notes, cache notes, update view
        view.setUpSpinner(
            listOf(
                FamilyMember("1", "Jan", "Kowalski"),
                FamilyMember("2", "Ania", "Kowalska")
            )
        )
        view.updateEvents(
            mapOf(
                view.currentDay.plusDays(1) to true,
                view.currentDay.plusDays(2) to true
            )
        )
    }

    fun familyMemberSelected(familyMemberId: String) {
        view.updateEvents(
            mapOf(
                view.currentDay.plusDays(1) to true,
                view.currentDay.plusDays(5) to true
            )
        )
    }

    fun monthChanged(yearMonth: YearMonth) = view.updateMonth(monthYearFormatter.format(yearMonth))

    fun dateSelected(date: LocalDate) {
        if (view.selectedDate != date) {
            view.updateDate(
                date, selectedDayFormatter.format(date), listOf(
                    EventViewModel(
                        "",
                        "titleNote2",
                        "contentNote2",
                        "placeNote2",
                        view.currentDay,
                        view.currentDay
                    ),
                    EventViewModel(
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

    fun eventClicked(event: EventViewModel) {
        Log.i("TMP", event.title)
    }
}