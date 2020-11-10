package com.example.virtualfridge.domain.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseFragment
import com.example.virtualfridge.domain.calendar.notes.NoteViewModel
import com.example.virtualfridge.domain.calendar.notes.NotesAdapter
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
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject

class CalendarFragment : BaseFragment() {
    // TODO: proper view/presenter split
    // TODO: download notes in
    // TODO: caching notes in

    @Inject
    lateinit var presenter: CalendarPresenter

    private lateinit var notesAdapter: NotesAdapter

    private val currentDay = LocalDate.now()
    private val notes = mutableMapOf<LocalDate, List<NoteViewModel>>()
    private val monthYearFormatter = DateTimeFormatter.ofPattern("MMM yyyy")

    var selectedDate: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notesAdapter = NotesAdapter {
            presenter.noteClicked(it)
        }
        notes[currentDay.plusDays(1)] = listOf(
            NoteViewModel("", "titleNote2", "contentNote2", "placeNote2", currentDay, currentDay),
            NoteViewModel("", "titleNote1", "contentNote1", "placeNote1", currentDay, currentDay)
        )
        return inflater.inflate(R.layout.fragment_calendar, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvNotes.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvNotes.adapter = notesAdapter

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

        setUpCalendarView(view)
        if (savedInstanceState == null) {
            calendarView.post {
                selectDate(currentDay)
            }
        }
    }

    fun updateCalendar(date: LocalDate, formattedDate: String) {
        selectedDate?.let { calendarView.notifyDateChanged(it) }
        selectedDate = date
        calendarView.notifyDateChanged(date)
        notesAdapter.setItems(notes[date].orEmpty())
        tvSelectedDay.text = formattedDate
    }

    private fun selectDate(date: LocalDate) {
        presenter.dateSelected(date)
    }

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
                            container.vDayOverlay.visible(notes.contains(day.date))
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
            tvMonth.text = monthYearFormatter.format(it.yearMonth)
        }
    }
}