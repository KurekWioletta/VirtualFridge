package com.example.virtualfridge.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualfridge.domain.calendar.notes.NoteViewHolder
import com.example.virtualfridge.domain.calendar.notes.NoteViewModel
import com.example.virtualfridge.domain.family.invitations.InvitationViewHolder

abstract class BaseViewComponentsViewModel()

abstract class BaseViewComponentsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: BaseViewComponentsViewModel)
}

class ViewComponentsAdapter<T>(
    private val customViewType: Int,
    private val mainClickAction: ((T) -> Unit)? = null,
    private val secondaryClickAction: ((T) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewComponentsViewHolder>() {

    private val items = mutableListOf<BaseViewComponentsViewModel>()

    fun setItems(items: List<BaseViewComponentsViewModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (customViewType) {
            NOTES -> NoteViewHolder(mainClickAction as ((NoteViewModel) -> Unit), parent)
            INVITATIONS -> InvitationViewHolder(
                mainClickAction as ((String) -> Unit),
                secondaryClickAction as ((String) -> Unit),
                parent
            )
            else -> throw IllegalArgumentException("view type unknown!")
        }

    override fun onBindViewHolder(viewHolder: BaseViewComponentsViewHolder, position: Int) =
        viewHolder.bind(items[position])

    override fun getItemCount(): Int = items.size

    companion object {
        const val NOTES = 1
        const val INVITATIONS = 2
    }
}