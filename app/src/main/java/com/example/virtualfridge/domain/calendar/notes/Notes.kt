package com.example.virtualfridge.domain.calendar.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualfridge.R
import kotlinx.android.synthetic.main.calendar_item_note.view.*
import java.time.LocalDate

// TODO: create base logic for setting up components

data class NoteViewModel(
    val id: String,
    val title: String,
    val text: String,
    val place: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)

class NoteViewHolder(
    private val onClick: ((NoteViewModel) -> Unit)? = null,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.calendar_item_note, parent, false)
) {
    fun bind(note: NoteViewModel) {
        itemView.tvTitle.text = note.title
        onClick?.let {
            itemView.setOnClickListener {
                onClick.invoke(note)
            }
        }
    }
}

class NotesAdapter(
    private val onClick: ((NoteViewModel) -> Unit)? = null
) : RecyclerView.Adapter<NoteViewHolder>() {

    private val items = mutableListOf<NoteViewModel>()

    fun setItems(items: List<NoteViewModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(onClick, parent)

    override fun onBindViewHolder(viewHolder: NoteViewHolder, position: Int) =
        viewHolder.bind(items[position])

    override fun getItemCount(): Int = items.size
}
