package com.example.virtualfridge.domain.calendar

import com.example.virtualfridge.data.api.EventsApi
import com.example.virtualfridge.data.api.FamilyApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.calendar.CalendarFragment.FamilyMemberViewModel
import com.example.virtualfridge.domain.calendar.CalendarFragment.FamilyMemberViewModel.Companion.fromResponse
import com.example.virtualfridge.domain.calendar.events.EventViewModel
import com.example.virtualfridge.utils.ApiErrorParser
import com.example.virtualfridge.utils.RxTransformerManager
import com.example.virtualfridge.utils.dateTimeFormatter
import com.example.virtualfridge.utils.rangeTo
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalendarFragmentPresenter @Inject constructor(
    private val view: CalendarFragment,
    private val eventsApi: EventsApi,
    private val familyApi: FamilyApi,
    private val userDataStore: UserDataStore,
    private val apiErrorParser: ApiErrorParser,
    private val rxTransformerManager: RxTransformerManager
) {
    private val cachedEvents = mutableMapOf<LocalDate, MutableList<EventViewModel>>()

    // przy emisji podobnie jak w BoardActivity bedziemy odswiezac kalendarz, ale mozna tez przekazac parametr
    // przy emisji eventu, tym parametrem bedzie id wybranego czlonka rodziny, zeby pokazac kalendarz dla wybranego czlonka
    private val triggerRefresh = BehaviorSubject.createDefault<String>("")
    private val monthYearFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectedDayFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")

    fun init() = view.registerViewSubscription(
        // zestawiamy dwa observable w jeden, zeby moc jednoczesnie handlowac errory z dwoch sygnałów
        Observable.combineLatest(
            // pobranie czlonkow rodziny
            familyApi.familyMembers(userDataStore.loggedInUser().id)
                .map { fromResponse(it) },
            // pobranie wydarzen dla odpowiedniego czlonka rodziny z systemem odswiezania jak w boardActivity
            triggerRefresh.flatMap { userId ->
                eventsApi.events(
                    if (userId.isEmpty()) {
                        userDataStore.loggedInUser().id
                    } else {
                        userId
                    }
                )
                    // mapowanie na EventViewModel
                    .map { EventViewModel.fromResponse(it) }
                    // wrzucamy powyzsze operatory na IO thread, a ponizsze na mainThread
                    .compose { rxTransformerManager.applyIOScheduler(it) }
            },
            // Pair(first, last) = first to last
            BiFunction { family: List<FamilyMemberViewModel>, events: List<EventViewModel> -> family to events }
        )
            // wrzucamy powyzsze operatory na IO thread, a ponizsze na mainThread
            .compose { rxTransformerManager.applyIOScheduler(it) }
            .doOnSubscribe { view.showLoading() }
            .doOnEach { view.hideLoading() }
            //  (first, last) = Pair()
            .subscribe({ (family, events) ->
                // wrzucamy powyzsze operatory na IO thread, a ponizsze na mainThread
                view.updateFamilyMembers(family)
                // usuniecie zcachowanych danych
                cachedEvents.clear()
                // przeiterowanie sie po eventach i dla kazdego eventu dodanie go do cache
                events.forEach {
                    // zeby wyswietlil sie uevent ktory trwa przez 5 dni to trzeba dodatkowo dodac
                    // go do naszej mapy w kazdy dzien w ktorym wydarzenie ma miec miejsce
                    val startDate = LocalDate.parse(it.startDate, dateTimeFormatter)
                    val endDate = LocalDate.parse(it.endDate, dateTimeFormatter)
                    // dlatego iterujemy po dacie startDate to endDate
                    for (date in startDate.rangeTo(endDate)) {
                        if (cachedEvents.contains(date)) {
                            cachedEvents[date]!!.add(it)
                        } else {
                            cachedEvents[date] = mutableListOf(it)
                        }
                    }
                }
                // update eventow, dla kalendarza potrzebna jest tylko informacja czy w danym dniu sa eventy (zeby pokazac kropke)
                view.updateEventsOnCalendar(cachedEvents.mapValues { true })
            }, {
                view.showAlert(apiErrorParser.parse(it))
            })
    )

    fun triggerRefresh(userId: String) {
        // przekazujemy parametr userId wyemitowanemu eventowi
        triggerRefresh.onNext(userId)
    }

    fun monthChanged(yearMonth: YearMonth) = view.updateMonth(monthYearFormatter.format(yearMonth))

    fun dateSelected(date: LocalDate) {
        if (view.selectedDate != date) {
            view.updateDate(
                date, selectedDayFormatter.format(date)
            )
            if (cachedEvents.containsKey(date)) {
                // update listy wydarzen na podstawie zachowanych danych
                view.updateEventsList(cachedEvents[view.selectedDate]!!)
            }
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
                view.showAlert(apiErrorParser.parse(it))
            })
        )
}