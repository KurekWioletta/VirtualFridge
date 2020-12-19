package com.example.virtualfridge.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualfridge.domain.board.notes.NotesViewHolder
import com.example.virtualfridge.domain.board.notes.NotesViewModel
import com.example.virtualfridge.domain.calendar.events.EventViewHolder
import com.example.virtualfridge.domain.calendar.events.EventViewModel
import com.example.virtualfridge.domain.family.invitations.InvitationViewHolder

// bazowy viewModel
abstract class BaseViewComponentsViewModel()

// bazowy viewHolder - przyklad w InvitationViewHolder
abstract class BaseViewComponentsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: BaseViewComponentsViewModel)
}

// generyczny adapter dla wszystkich recycler view uzywanych w aplikacji
// przekazujemy typ i dwie akcje (opcjonalnie), ktore beda sie wykonywac po kliknieciu w komponent (np element listy u nas)
class ViewComponentsAdapter<T>(
    private val customViewType: Int,
    private val mainClickAction: ((T) -> Unit)? = null,
    private val secondaryClickAction: ((T) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewComponentsViewHolder>() {

    // wszystkie elementy recycler view - nie wszystkie elementy sa renderowane, ale trzymane w pamieci sa wszystkie
    // renderowanie odbywa sie podczas procesu scrollowania
    // recycler view dziala w oparciu o view modele i view holdery (ktore sa w bezposredniej relacji ze soba)
    // view model zawiera dane, ktore beda wyswietlane w widoku renerowanym za pomoca viewHolderu
    private val items = mutableListOf<BaseViewComponentsViewModel>()

    // update elementow recyclerView
    fun setItems(items: List<BaseViewComponentsViewModel>) {
        this.items.clear()
        this.items.addAll(items)
        // potrzebne aby adapter wiedzial ze musi odswiezyc (zrenderowac) wszystkie komponenty raz jeszcze
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        // na podstawie typu komponentu zwracamy odpowiedni viewHolder, ktory bedzie mial byc wyrenderowany
        when (customViewType) {
            NOTES -> NotesViewHolder(mainClickAction as ((NotesViewModel) -> Unit), parent)
            EVENTS -> EventViewHolder(mainClickAction as ((EventViewModel) -> Unit), parent)
            INVITATIONS -> InvitationViewHolder(
                mainClickAction as ((String) -> Unit),
                secondaryClickAction as ((String) -> Unit),
                parent
            )
            else -> throw IllegalArgumentException("view type unknown!")
        }

    // przypisanie danych viewModel do viewHolderu
    override fun onBindViewHolder(viewHolder: BaseViewComponentsViewHolder, position: Int) =
        viewHolder.bind(items[position])

    override fun getItemCount(): Int = items.size

    companion object {
        const val NOTES = 1
        const val EVENTS = 2
        const val INVITATIONS = 3
    }
}