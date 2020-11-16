package com.example.virtualfridge.domain.createNote

import android.content.Intent
import android.os.Bundle
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseActivity
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
                etTitle.text.toString(),
                etDescription.text.toString(),
                etPlace.text.toString(),
                etStartDate.text.toString(),
                etEndDate.text.toString()
            )
        }
    }

    fun showValidationResults(validationViewModel: ValidationViewModel) {
        etTitle.error = validationViewModel.titleError
    }

    data class ValidationViewModel(
        val titleError: String?
    ) : BaseValidationViewModel() {
        override fun toList() = listOf(titleError)
    }

    companion object {
        fun getIntent(activity: BaseActivity) = Intent(activity, CreateNoteActivity::class.java)
    }
}