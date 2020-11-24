package com.example.virtualfridge.domain.board.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.virtualfridge.R
import com.example.virtualfridge.utils.BaseViewComponentsViewHolder
import com.example.virtualfridge.utils.BaseViewComponentsViewModel
import kotlinx.android.synthetic.main.calendar_item_event.view.*

data class NotesViewModel(
    val id: String,
    val text: String
) : BaseViewComponentsViewModel()

class NotesViewHolder(
    private val onClick: ((NotesViewModel) -> Unit),
    parent: ViewGroup
) : BaseViewComponentsViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.board_item_notes, parent, false)
) {

    override fun bind(item: BaseViewComponentsViewModel) {
        item as NotesViewModel

        itemView.tvTitle.text = item.text
        itemView.setOnClickListener {
            onClick.invoke(item)
//            true
        }
    }
}