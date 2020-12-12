package com.example.virtualfridge.domain.calendar.events

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.models.EventResponse
import com.example.virtualfridge.utils.BaseViewComponentsViewHolder
import com.example.virtualfridge.utils.BaseViewComponentsViewModel
import kotlinx.android.synthetic.main.calendar_item_event.view.*

data class EventViewModel(
    val id: String,
    val title: String,
    val description: String?,
    val place: String?,
    val startDate: String,
    val endDate: String
) : BaseViewComponentsViewModel() {

    companion object {
        fun fromResponse(response: List<EventResponse>) = response.map {
            EventViewModel(
                it.id,
                it.title,
                it.description,
                it.place,
                it.startDate,
                it.endDate
            )
        }
    }
}

class EventViewHolder(
    private val onClick: ((EventViewModel) -> Unit),
    parent: ViewGroup
) : BaseViewComponentsViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.calendar_item_event, parent, false)
) {

    override fun bind(item: BaseViewComponentsViewModel) {
        item as EventViewModel

        itemView.tvTitle.text = item.title
        itemView.tvDescription.text = item.description
        itemView.tvPlace.text = item.place
        itemView.tvStartDate.text = item.startDate
        itemView.tvEndDate.text = item.endDate
        itemView.setOnClickListener {
            onClick.invoke(item)
        }
    }
}
