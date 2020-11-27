package com.example.virtualfridge.domain.calendar

import com.example.virtualfridge.data.api.EventsApi
import com.example.virtualfridge.data.api.FamilyApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.calendar.CalendarFragment.FamilyMember
import com.example.virtualfridge.domain.calendar.events.EventViewModel
import com.example.virtualfridge.utils.RxTransformerManager
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import okhttp3.ResponseBody
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalendarFragmentPresenter @Inject constructor(
    private val view: CalendarFragment,
    private val eventsApi: EventsApi,
    private val familyApi: FamilyApi,
    private val userDataStore: UserDataStore,
    private val rxTransformerManager: RxTransformerManager
) {
    private val events = mutableMapOf<LocalDate, List<EventViewModel>>()

    private val triggerRefresh = BehaviorSubject.createDefault<String>("")
    private val monthYearFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectedDayFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")

    fun init() = view.registerViewSubscription(
        Observable.combineLatest(
            familyApi.familyMembers(userDataStore.loggedInUser().id!!),
            triggerRefresh.map { userId ->
                eventsApi.events(
                    if (userId.isEmpty()) {
                        userDataStore.loggedInUser().id!!
                    } else {
                        userId
                    }
                )
            },
            BiFunction { family: ResponseBody, events: Observable<ResponseBody> -> family to events }
        )
            .compose { rxTransformerManager.applyIOScheduler(it) }
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe({
                view.updateFamilyMembers(
                    listOf(
                        FamilyMember("1", "Jan", "Kowalski"),
                        FamilyMember("2", "Ania", "Kowalska")
                    )
                )
                view.updateEventsOnCalendar(
                    mapOf(
                        view.currentDay.plusDays(1) to true,
                        view.currentDay.plusDays(2) to true
                    )
                )
            }, {
//                view.showAlert("ERROR")
                view.updateFamilyMembers(
                    listOf(
                        FamilyMember("1", "Jan", "Kowalski"),
                        FamilyMember("2", "Ania", "Kowalska")
                    )
                )
                view.updateEventsOnCalendar(
                    mapOf(
                        view.currentDay.plusDays(1) to true,
                        view.currentDay.plusDays(2) to true
                    )
                )
            })
    )

    fun triggerRefresh(userId: String) {
        triggerRefresh.onNext(userId)
    }

    fun monthChanged(yearMonth: YearMonth) = view.updateMonth(monthYearFormatter.format(yearMonth))

    fun dateSelected(date: LocalDate) {
        if (view.selectedDate != date) {
            view.updateDate(
                date, selectedDayFormatter.format(date)
            )
            view.updateEventsList(
                listOf(
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

    fun editEvent(event: EventViewModel) {

    }

    fun deleteEvent(eventId: String) =
        view.registerViewSubscription(eventsApi.deleteEvent(eventId)
            .compose { rxTransformerManager.applyIOScheduler(it) }
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe({
                triggerRefresh(view.currentFamilyMemberId())
            }, {
                view.showAlert("ERROR")
            })
        )
}