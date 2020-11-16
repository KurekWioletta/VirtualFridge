package com.example.virtualfridge.domain.calendar.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.virtualfridge.R
import com.example.virtualfridge.utils.BaseViewComponentsViewHolder
import com.example.virtualfridge.utils.BaseViewComponentsViewModel
import kotlinx.android.synthetic.main.calendar_item_note.view.*
import java.time.LocalDate

data class NoteViewModel(
    val id: String,
    val title: String,
    val text: String,
    val place: String,
    val startDate: LocalDate,
    val endDate: LocalDate
) : BaseViewComponentsViewModel()

class NoteViewHolder(
    private val onClick: ((NoteViewModel) -> Unit),
    parent: ViewGroup
) : BaseViewComponentsViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.calendar_item_note, parent, false)
) {

    override fun bind(item: BaseViewComponentsViewModel) {
        item as NoteViewModel

        itemView.tvTitle.text = item.title
        itemView.setOnClickListener {
            onClick.invoke(item)
        }
    }
}
