package com.example.virtualfridge.domain.calendar

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.models.FamilyMemberResponse
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.base.BaseFragment
import com.example.virtualfridge.domain.calendar.events.EventViewModel
import com.example.virtualfridge.domain.createEvent.CreateEventActivity
import com.example.virtualfridge.domain.createEvent.CreateEventActivity.Companion.RC_CREATE_EVENT
import com.example.virtualfridge.utils.ViewComponentsAdapter
import com.example.virtualfridge.utils.ViewComponentsAdapter.Companion.EVENTS
import com.example.virtualfridge.utils.invisible
import com.example.virtualfridge.utils.visible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import kotlinx.android.synthetic.main.calendar_day_layout.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject


class CalendarFragment : BaseFragment() {

    @Inject
    lateinit var presenter: CalendarFragmentPresenter

    private lateinit var eventsAdapter: ViewComponentsAdapter<EventViewModel>
    private lateinit var familyMembersAdapterViewModel: ArrayAdapter<FamilyMemberViewModel>

    private val events = mutableMapOf<LocalDate, Boolean>()

    val currentDay: LocalDate = LocalDate.now()
    var selectedDate: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsAdapter = ViewComponentsAdapter(EVENTS, {
            showSelectEventOptionDialog(it)
        })
        familyMembersAdapterViewModel =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)

        return inflater.inflate(R.layout.fragment_calendar, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.init()

        rvEvents.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvEvents.adapter = eventsAdapter

        spFamilyMembers.adapter = familyMembersAdapterViewModel
        spFamilyMembers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {}

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                presenter.triggerRefresh(currentFamilyMemberId())
            }
        }

        ivNextMonth.setOnClickListener {
            calendarView.findFirstVisibleMonth()?.let {
                calendarView.smoothScrollToMonth(it.yearMonth.next)
            }
        }
        ivPreviousMonth.setOnClickListener {
            calendarView.findFirstVisibleMonth()?.let {
                calendarView.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
        fabCreateEvent.setOnClickListener {
            startActivity(CreateEventActivity.getIntent(activity as BaseActivity))
        }

        setUpCalendarView(view)
        if (savedInstanceState == null) {
            calendarView.post {
                selectDate(currentDay)
            }
        }
        updateFamilyMembers(
            listOf(
                FamilyMemberViewModel("1", "Jan", "Kowalski"),
                FamilyMemberViewModel("2", "Ania", "Kowalska")
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_CREATE_EVENT) {
            presenter.triggerRefresh(currentFamilyMemberId())
        }
    }

    fun updateDate(date: LocalDate, formattedDate: String) {
        selectedDate?.let { calendarView.notifyDateChanged(it) }
        selectedDate = date
        calendarView.notifyDateChanged(date)
        tvSelectedDay.text = formattedDate
    }

    fun updateMonth(formattedDate: String) {
        tvMonth.text = formattedDate
    }

    fun updateEventsList(events: List<EventViewModel>) = eventsAdapter.setItems(events)

    fun updateEventsOnCalendar(events: Map<LocalDate, Boolean>) {
        this.events.clear()
        this.events.putAll(events)
        events.keys.forEach {
            calendarView.adapter?.run {
                calendarView.notifyDateChanged(it)
            }
        }
    }

    fun updateFamilyMembers(members: List<FamilyMemberViewModel>) {
        familyMembersAdapterViewModel.clear()
        familyMembersAdapterViewModel.addAll(members)
    }

    fun currentFamilyMemberId(): String = (spFamilyMembers.selectedItem as FamilyMemberViewModel).id

    private fun selectDate(date: LocalDate) = presenter.dateSelected(date)

    private fun showSelectEventOptionDialog(event: EventViewModel) =
        AlertDialog.Builder(context)
            .setItems(
                arrayOf(
                    getString(R.string.calendar_edit_event),
                    getString(R.string.calendar_delete_event)
                )
            ) { _, which ->
                when (which) {
                    0 -> {
                        presenter.editEvent(event)
                    }
                    1 -> {
                        presenter.deleteEvent(event.id)
                    }
                }
            }
            .create()
            .show()

    private fun setUpCalendarView(rootView: View) {
        val currentMonth = YearMonth.now()
        calendarView.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            WeekFields.of(Locale.getDefault()).firstDayOfWeek
        )
        calendarView.scrollToMonth(currentMonth)

        class DayViewContainer(view: View) : ViewContainer(view) {
            val tvDay: TextView = view.tvDay
            val vDayOverlay: View = view.vDayOverlay

            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                container.tvDay.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    when (day.date) {
                        selectedDate -> {
                            container.tvDay.markDay(
                                rootView.context,
                                R.drawable.bg_selected_day,
                                R.color.white
                            )
                            container.vDayOverlay.invisible()
                        }
                        currentDay -> {
                            container.tvDay.markDay(
                                rootView.context,
                                R.drawable.bg_current_day,
                                R.color.white
                            )
                            container.vDayOverlay.invisible()
                        }
                        else -> {
                            container.tvDay.markDay(rootView.context, null, R.color.calendar_black)
                            container.vDayOverlay.visible(events.contains(day.date))
                        }
                    }
                } else {
                    container.tvDay.invisible()
                    container.vDayOverlay.invisible()
                }
            }
        }
        calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<ViewContainer> {
            override fun create(view: View) = ViewContainer(view)
            override fun bind(container: ViewContainer, month: CalendarMonth) {
            }
        }
        calendarView.monthScrollListener = {
            presenter.monthChanged(it.yearMonth)
        }
    }

    data class FamilyMemberViewModel(
        val id: String,
        val firstName: String,
        val lastName: String
    ) {
        override fun toString(): String = "$firstName $lastName"

        companion object {
            fun fromResponse(response: List<FamilyMemberResponse>) = response.map {
                FamilyMemberViewModel(it.id, it.firstName, it.lastName)
            }
        }
    }
}