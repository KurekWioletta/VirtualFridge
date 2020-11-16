package com.example.virtualfridge.domain.createNote

import com.example.virtualfridge.R
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import javax.inject.Inject

class CreateNoteActivityPresenter @Inject constructor(
    private val view: CreateNoteActivity
) {

    fun createNoteClicked(
        title: String,
        description: String?,
        place: String?,
        startDate: String,
        endDate: String
    ) {
        val validationViewModel = CreateNoteActivity.ValidationViewModel(
            title.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            // TODO: Save note on backend + locally
            view.finish()
        }
    }
}