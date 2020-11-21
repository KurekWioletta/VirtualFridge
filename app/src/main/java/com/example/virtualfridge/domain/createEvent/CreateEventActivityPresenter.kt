package com.example.virtualfridge.domain.createEvent

import com.example.virtualfridge.R
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import javax.inject.Inject

class CreateEventActivityPresenter @Inject constructor(
    private val view: CreateEventActivity
) {

    fun createEventClicked(
        title: String,
        description: String?,
        place: String?,
        startDate: String,
        endDate: String
    ) {
        val validationViewModel = CreateEventActivity.ValidationViewModel(
            title.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            // TODO: Save note on backend + locally
            view.finish()
        }
    }
}