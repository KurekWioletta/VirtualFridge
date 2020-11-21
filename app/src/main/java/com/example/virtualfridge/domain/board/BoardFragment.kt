package com.example.virtualfridge.domain.board

import android.app.AlertDialog
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
        adapter = ViewComponentsAdapter(NOTES, {
            showDeleteNoteDialog(it.id)
        })
        return inflater.inflate(R.layout.fragment_board, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()

        rvNotes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvNotes.adapter = adapter

        fabCreateNote.setOnClickListener {
            startActivity(CreateNoteActivity.getIntent(activity as BaseActivity))
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