package com.example.virtualfridge.domain.calendar.events

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.virtualfridge.R
import com.example.virtualfridge.utils.BaseViewComponentsViewHolder
import com.example.virtualfridge.utils.BaseViewComponentsViewModel
import kotlinx.android.synthetic.main.calendar_item_event.view.*
import java.time.LocalDate

data class EventViewModel(
    val id: String,
    val title: String,
    val text: String,
    val place: String,
    val startDate: LocalDate,
    val endDate: LocalDate
) : BaseViewComponentsViewModel()

class EventViewHolder(
    private val onClick: ((EventViewModel) -> Unit),
    parent: ViewGroup
) : BaseViewComponentsViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.calendar_item_event, parent, false)
) {

    override fun bind(item: BaseViewComponentsViewModel) {
        item as EventViewModel

        itemView.tvTitle.text = item.title
        itemView.setOnClickListener {
            onClick.invoke(item)
//            true
        }
    }
}
