package com.example.virtualfridge.domain.createNote

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.calendar.CalendarFragment.FamilyMember
import com.example.virtualfridge.utils.BaseValidationViewModel
import kotlinx.android.synthetic.main.activity_create_note.*
import javax.inject.Inject

class CreateNoteActivity : BaseActivity() {

    @Inject
    lateinit var presenter: CreateNoteActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        btnCreateNote.setOnClickListener {
            presenter.createNoteClicked(
                (spFamilyMembers.selectedItem as FamilyMember).id,
                etNote.text.toString()
            )
        }

        presenter.init()
    }

    fun setUpSpinner(members: List<FamilyMember>) {
        spFamilyMembers.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, members)
    }

    fun showValidationResults(validationViewModel: ValidationViewModel) {
        etNote.error = validationViewModel.noteError
    }

    data class ValidationViewModel(
        val noteError: String?
    ) : BaseValidationViewModel() {
        override fun toList() = listOf(noteError)
    }

    companion object {
        fun getIntent(activity: BaseActivity) = Intent(activity, CreateNoteActivity::class.java)
    }
}
