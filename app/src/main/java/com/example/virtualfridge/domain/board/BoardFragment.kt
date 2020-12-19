package com.example.virtualfridge.domain.board

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.base.BaseFragment
import com.example.virtualfridge.domain.board.notes.NotesViewModel
import com.example.virtualfridge.domain.createNote.CreateNoteActivity
import com.example.virtualfridge.domain.createNote.CreateNoteActivity.Companion.RC_CREATE_NOTE
import com.example.virtualfridge.utils.ViewComponentsAdapter
import com.example.virtualfridge.utils.ViewComponentsAdapter.Companion.NOTES
import kotlinx.android.synthetic.main.fragment_board.*
import javax.inject.Inject


class BoardFragment : BaseFragment() {

    @Inject
    lateinit var presenter: BoardFragmentPresenter

    private lateinit var adapter: ViewComponentsAdapter<NotesViewModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inicjalizacja adaptera dla reyclerView - typ viewModelu i wykonywana akcja na kliknieciu w element recyclerView
        adapter = ViewComponentsAdapter(NOTES, {
            showDeleteNoteDialog(it.id)
        })
        return inflater.inflate(R.layout.fragment_board, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()

        // recyclerView potrzebuje layoutManager i adaptera
        // layoutManager odpowiada za to jak nasz recyclerView bedzie sie zachowywał/wygladal, u nas
        // dla board mamy forme tabeli 2 kolumnowej wertykalnie
        rvNotes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvNotes.adapter = adapter

        // otworzenie aktywnosci na floating button
        fabCreateNote.setOnClickListener {
            startActivityForResult(
                CreateNoteActivity.getIntent(activity as BaseActivity),
                RC_CREATE_NOTE
            )
        }
    }

    // jesli activity CreateNoteActivity zwroci nam result to odswiezamy nasze notatki
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_CREATE_NOTE) {
            presenter.refreshBoard()
        }
    }

    fun updateNotes(notes: List<NotesViewModel>) = adapter.setItems(notes)

    private fun showDeleteNoteDialog(noteId: String) =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.board_delete_note))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                presenter.deleteNoteClicked(noteId)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
            .show()
}